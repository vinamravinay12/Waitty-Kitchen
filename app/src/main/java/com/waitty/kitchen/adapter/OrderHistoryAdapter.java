package com.waitty.kitchen.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.waitty.kitchen.R;
import com.waitty.kitchen.activity.HomeActivity;
import com.waitty.kitchen.appinterface.getResponseData;
import com.waitty.kitchen.constant.constant;
import com.waitty.kitchen.databinding.AdapterNewOrderBinding;
import com.waitty.kitchen.databinding.DialogLogoutBinding;
import com.waitty.kitchen.model.OrderDetails;
import com.waitty.kitchen.retrofit.API;
import com.waitty.kitchen.retrofit.APICall;
import com.waitty.kitchen.retrofit.ApiClient;
import com.waitty.kitchen.retrofit.ApiInterface;
import com.waitty.kitchen.utility.Dialog;
import com.waitty.kitchen.utility.MyLoading;
import com.waitty.kitchen.utility.Utility;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>{

    private LinkedList<OrderDetails> dataList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private int orderType;
    private MyLoading loader;
    private AlertDialog dialogDoneOrder;

    // Class constructor
    public OrderHistoryAdapter(Context context, LinkedList<OrderDetails> data, int typeID) {
        this.dataList = data;
        orderType=typeID;
        this.mContext = context;
        loader = new MyLoading(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterNewOrderBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_new_order, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderDetails data=dataList.get(position);

        if(data.getWaiter()!=null) {
            holder.adapterNewOrderBinding.txtWaiterID.setText(mContext.getString(R.string.txt_waiter_id_hash) + data.getWaiter().getKey());
            holder.adapterNewOrderBinding.txtName.setText(data.getWaiter().getName());
        }

        if(data.getTable().getName().contains(mContext.getString(R.string.txt_table_no)))
            holder.adapterNewOrderBinding.txtTableNo.setText(mContext.getString(R.string.txt_t)+" "+data.getTable().getName().replace(mContext.getString(R.string.txt_table_no),"").trim());
        else
            holder.adapterNewOrderBinding.txtTableNo.setText(mContext.getString(R.string.txt_t)+" "+data.getTable().getName().replace(mContext.getString(R.string.txt_take_away),"").trim());

        holder.adapterNewOrderBinding.txtOrderType.setText(data.getOrderType());
        if(data.getOrderType().contains(mContext.getString(R.string.txt_dinein)))
            holder.adapterNewOrderBinding.txtOrderType.setBackgroundResource(R.drawable.round_selected_submenu);
        else
            holder.adapterNewOrderBinding.txtOrderType.setBackgroundResource(R.drawable.round_take_away);

        LinearLayoutManager LinLayManager = new LinearLayoutManager(mContext);
        LinLayManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.adapterNewOrderBinding.rcvItem.setLayoutManager(LinLayManager);
        OrderHistoryItemAdapter adapter = new OrderHistoryItemAdapter(mContext, data.getOrderItems(), data.getPaymentSymbol(), orderType,position);
        holder.adapterNewOrderBinding.rcvItem.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (data.getComment().trim().isEmpty()) {
            holder.adapterNewOrderBinding.viewLineComment1.setVisibility(View.GONE);
            holder.adapterNewOrderBinding.txtComment.setVisibility(View.GONE);
            holder.adapterNewOrderBinding.viewLineComment2.setVisibility(View.GONE);
        }else{
            holder.adapterNewOrderBinding.viewLineComment1.setVisibility(View.VISIBLE);
            holder.adapterNewOrderBinding.txtComment.setVisibility(View.VISIBLE);
            holder.adapterNewOrderBinding.txtComment.setText(Html.fromHtml(data.getComment()));
            holder.adapterNewOrderBinding.viewLineComment2.setVisibility(View.VISIBLE);
        }

        holder.adapterNewOrderBinding.txtStartPreparing.setEnabled(true);
        holder.adapterNewOrderBinding.layLinETA.setVisibility(View.GONE);

        if(orderType==1) {
            holder.adapterNewOrderBinding.txtStartPreparing.setText(mContext.getString(R.string.txt_start_preparing));
        }else{
            holder.adapterNewOrderBinding.layLinETA.setVisibility(View.VISIBLE);
            holder.adapterNewOrderBinding.txtCounter.setVisibility(View.GONE);

            if(data.getOrderArrivingTimeMinits().trim().isEmpty())
                holder.adapterNewOrderBinding.edtETA.setText("");
            else {
                holder.adapterNewOrderBinding.edtETA.setText(data.getOrderArrivingTimeMinits().trim());

                if(holder.timer!=null)
                    holder.timer.cancel();

                if(!data.getOrderArrivingTime().isEmpty()) {

                    try{
                        holder.adapterNewOrderBinding.txtCounter.setVisibility(View.VISIBLE);
                        Date arrivalDate = constant.dateFormaterServer.parse(Utility.chageUTC_ToLocalDate(constant.dateFormaterServer,data.getOrderArrivingTime().trim(),constant.dateFormaterServer));
                        Date currentDate= constant.dateFormaterServer.parse(constant.dateFormaterServer.format(Calendar.getInstance().getTime()));

                        if(arrivalDate.before(currentDate))
                            holder.adapterNewOrderBinding.txtCounter.setText(R.string.text_time_over);
                        else {

                            holder.timer = new CountDownTimer(arrivalDate.getTime() - currentDate.getTime(), 1000) { // adjust the milli seconds here

                                public void onTick(long millisUntilFinished) {

                                    holder.adapterNewOrderBinding.txtCounter.setText("" + String.format("%02d:%02d:%02d",
                                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                }

                                public void onFinish() {
                                    holder.adapterNewOrderBinding.txtCounter.setText(R.string.text_time_over);
                                }
                            }.start();
                        }

                    }catch (Exception e){e.printStackTrace();}

                }else
                    holder.adapterNewOrderBinding.txtCounter.setVisibility(View.GONE);

            }

            holder.adapterNewOrderBinding.txtStartPreparing.setText(mContext.getString(R.string.txt_done));

            boolean allItemDone=true;
            for(int i=0;i<data.getOrderItems().size();i++){
                if(!data.getOrderItems().get(i).getIsPrepared() && data.getOrderItems().get(i).getIn_stock()>0)
                    allItemDone=false;
            }

            if(allItemDone)
                holder.adapterNewOrderBinding.txtStartPreparing.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrderReady));
            else{
                holder.adapterNewOrderBinding.txtStartPreparing.setBackgroundColor(mContext.getResources().getColor(R.color.colorEdtHint));
                holder.adapterNewOrderBinding.txtStartPreparing.setEnabled(false);
            }
        }

        holder.adapterNewOrderBinding.txtStartPreparing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderType==1) {
                    if(Utility.isNetworkAvailable(mContext))
                        orderStartPreparingAPI(data.getId());
                    else
                        Utility.ShowToast(mContext,mContext.getString(R.string.check_network),0);
                }else {
                    InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    showDialogDoneOrder(data.getId());
                }
            }
        });

        holder.adapterNewOrderBinding.edtETA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)){
                    InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    if(!holder.adapterNewOrderBinding.edtETA.getText().toString().trim().isEmpty() && Integer.parseInt(holder.adapterNewOrderBinding.edtETA.getText().toString().trim())>0){
                        if(Utility.isNetworkAvailable(mContext))
                            setETAMinutesAPI(holder.adapterNewOrderBinding.edtETA.getText().toString().trim(), data.getId(), position);
                        else {
                            notifyItemChanged(position);
                            Utility.ShowToast(mContext, mContext.getString(R.string.check_network), 0);
                        }
                    }else
                        notifyItemChanged(position);

                }

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // View holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CountDownTimer timer;

        private AdapterNewOrderBinding adapterNewOrderBinding;

        public ViewHolder(AdapterNewOrderBinding itemBinding) {
            super(itemBinding.getRoot());
            this.adapterNewOrderBinding = itemBinding;
        }
    }

    // Notify list
    public void listNotify(int mainItemPosition){
        notifyItemChanged(mainItemPosition);
    }

    // Order start preparing API
    private void orderStartPreparingAPI(int orderID) {
        try{
            loader.show(mContext.getString(R.string.wait));
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty(API.ORDER_ID,orderID);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiInterface.orderStartPreparing(jsonObject,Utility.getSharedPreferencesString(mContext, constant.USER_SECURITY_TOKEN));
            new APICall(mContext).Server_Interaction(call, new getResponseData() {
                @Override
                public void onSuccess(JSONObject OBJ, String msg, String typeAPI) {
                    loader.dismiss();
                    Utility.ShowToast(mContext,msg,0);
                    ((HomeActivity)mContext).adapter.newOrderListFragment.refreshList(false);
                    ((HomeActivity)mContext).adapter.preparingOrderListFragment.refreshList(false);
                }

                @Override
                public void onFailed(String msg, String typeAPI) {
                    loader.dismiss();
                    if(!msg.isEmpty()){
                        Dialog.orderAlreadyStartDialog(mContext,mContext.getString(R.string.txt_alert),msg);
                        ((HomeActivity)mContext).adapter.newOrderListFragment.refreshList(false);
                    }

                }
            }, API.ORDER_START_PREPARING);

        }catch (Exception e){
            loader.dismiss();
            e.printStackTrace();
        }

    }

    // Order done popup
    private void showDialogDoneOrder(final int orderID) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_logout, null, false);
        builder.setView(dialogLogoutBinding.getRoot());

        dialogLogoutBinding.txtHeading.setText(R.string.txt_order_done);
        dialogLogoutBinding.txtMSG.setText(R.string.txt_order_done_msg);
        dialogLogoutBinding.btnLogout.setText(R.string.txt_done);

        dialogDoneOrder = builder.create();
        dialogDoneOrder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDoneOrder.setCanceledOnTouchOutside(false);
        dialogDoneOrder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogDoneOrder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogDoneOrder.show();

        dialogLogoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDoneOrder.dismiss();
            }
        });

        dialogLogoutBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isNetworkAvailable(mContext)){
                    dialogDoneOrder.dismiss();
                    orderDoneAPI(orderID);
                }else
                    Utility.ShowToast(mContext,mContext.getString(R.string.check_network),0);
            }
        });
    }

    // Order DONE API
    private void orderDoneAPI(int orderID) {
        try{
            loader.show(mContext.getString(R.string.wait));
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty(API.ORDER_ID,orderID);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiInterface.doneOrder(jsonObject,Utility.getSharedPreferencesString(mContext, constant.USER_SECURITY_TOKEN));
            new APICall(mContext).Server_Interaction(call, new getResponseData() {
                @Override
                public void onSuccess(JSONObject OBJ, String msg, String typeAPI) {
                    loader.dismiss();
                    Utility.ShowToast(mContext,msg,0);
                    ((HomeActivity)mContext).adapter.preparingOrderListFragment.refreshList(false);
                }

                @Override
                public void onFailed(String msg, String typeAPI) {
                    loader.dismiss();
                    if(!msg.isEmpty()){
                        Utility.ShowToast(mContext,msg,0);
                    }

                }
            }, API.DONE_ORDER);

        }catch (Exception e){
            loader.dismiss();
            e.printStackTrace();
        }
    }

    // Set ETA minutes on order
    private void setETAMinutesAPI(final String minutes, int orderID, final int position) {

        try{
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty(API.ORDER_ARRIVING_TIME,minutes);
            jsonObject.addProperty(API.ORDER_ID,orderID);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiInterface.setOrderETA(jsonObject,Utility.getSharedPreferencesString(mContext, constant.USER_SECURITY_TOKEN));
            new APICall(mContext).Server_Interaction(call, new getResponseData() {
                @Override
                public void onSuccess(JSONObject OBJ, String msg, String typeAPI) {
                    try{
                        Utility.ShowToast(mContext,msg,0);
                        dataList.get(position).setOrderArrivingTimeMinits(minutes);
                        dataList.get(position).setOrderArrivingTime(OBJ.getJSONObject(API.DATA).getString(API.ORDER_ARRIVING_TIME));
                        notifyDataSetChanged();
                    }catch (Exception e){e.printStackTrace();}

                }

                @Override
                public void onFailed(String msg, String typeAPI) {
                    if(!msg.isEmpty()){
                        Utility.ShowToast(mContext,msg,0);
                        notifyDataSetChanged();
                    }

                }
            }, API.SET_ORDER_ETA);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
