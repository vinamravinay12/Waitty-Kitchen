package com.waitty.kitchen.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.waitty.kitchen.R;
import com.waitty.kitchen.activity.HomeActivity;
import com.waitty.kitchen.appinterface.getResponseData;
import com.waitty.kitchen.constant.constant;
import com.waitty.kitchen.databinding.AdapterOrderItemBinding;
import com.waitty.kitchen.model.OrderDetails;
import com.waitty.kitchen.retrofit.API;
import com.waitty.kitchen.retrofit.APICall;
import com.waitty.kitchen.retrofit.ApiClient;
import com.waitty.kitchen.retrofit.ApiInterface;
import com.waitty.kitchen.utility.Utility;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;

public class OrderHistoryItemAdapter extends RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder>{

    private List<OrderDetails.OrderItem> dataList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private String paymentSymbol;
    private int orderType,mainItemPosition;

    // Class constructor
    public OrderHistoryItemAdapter(Context context, List<OrderDetails.OrderItem> orderItems, String symbol, int tab,int itemPosition) {
        this.dataList = orderItems;
        this.paymentSymbol = symbol;
        this.orderType=tab;
        this.mContext = context;
        this.mainItemPosition = itemPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterOrderItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_order_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final OrderDetails.OrderItem data=dataList.get(position);

        holder.adapterOrderItemBinding.txtName.setText(data.getDishDetails().getName());
        holder.adapterOrderItemBinding.txtQuantity.setText(String.valueOf(data.getQuantity()));

        if(orderType==1){
            holder.adapterOrderItemBinding.layLinDone.setVisibility(View.GONE);
        }else{
            holder.adapterOrderItemBinding.layLinDone.setVisibility(View.VISIBLE);

            if(data.getIn_stock()==0) {
                holder.adapterOrderItemBinding.layLinDone.setEnabled(false);
                holder.adapterOrderItemBinding.layLinDone.setBackgroundResource(R.drawable.round_outof_stock);
                holder.adapterOrderItemBinding.txtDone.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                holder.adapterOrderItemBinding.imvDone.setVisibility(View.VISIBLE);
            }else{
                holder.adapterOrderItemBinding.layLinDone.setEnabled(true);
                if(data.getIsPrepared()){
                    holder.adapterOrderItemBinding.layLinDone.setBackgroundResource(R.drawable.round_done);
                    holder.adapterOrderItemBinding.txtDone.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    holder.adapterOrderItemBinding.imvDone.setVisibility(View.VISIBLE);
                }else{
                    holder.adapterOrderItemBinding.layLinDone.setBackgroundResource(R.drawable.round_undone);
                    holder.adapterOrderItemBinding.txtDone.setTextColor(mContext.getResources().getColor(R.color.colorNextWelcome));
                    holder.adapterOrderItemBinding.imvDone.setVisibility(View.GONE);
                }
            }
        }

        if(data.getOrderItemCustomizations()!=null && data.getOrderItemCustomizations().size()>0){
            holder.adapterOrderItemBinding.txtCustomization1.setVisibility(View.GONE);
            holder.adapterOrderItemBinding.txtCustomization2.setVisibility(View.GONE);
            holder.adapterOrderItemBinding.txtCustomization3.setVisibility(View.GONE);

            for(int i=0;i<data.getOrderItemCustomizations().size();i++){
                if(i==0) {
                    String optionHint="";
                    if(data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().size()>0) {
                        for (int j = 0; j < data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().size();j++){
                            if(optionHint.isEmpty())
                                optionHint=data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().get(j).getOrderCustomizationOptionDetails().getName();
                            else
                                optionHint=optionHint+", "+data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().get(j).getOrderCustomizationOptionDetails().getName();
                        }
                    }

                    if(!optionHint.isEmpty()) {
                        holder.adapterOrderItemBinding.txtCustomization1.setVisibility(View.VISIBLE);
                        String text = "<font color=#777670>" + data.getOrderItemCustomizations().get(i).getOrderCustomizationDetails().getName() + ": " + "</font> <font color=#292323>" + optionHint + "</font>";
                        holder.adapterOrderItemBinding.txtCustomization1.setText(Html.fromHtml(text));
                    }
                }else if(i==1) {
                    String optionHint="";
                    if(data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().size()>0) {
                        for (int j = 0; j < data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().size();j++){
                            if(optionHint.isEmpty())
                                optionHint=data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().get(j).getOrderCustomizationOptionDetails().getName();
                            else
                                optionHint=optionHint+", "+data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().get(j).getOrderCustomizationOptionDetails().getName();
                        }
                    }

                    if(!optionHint.isEmpty()) {
                        holder.adapterOrderItemBinding.txtCustomization2.setVisibility(View.VISIBLE);
                        String text = "<font color=#777670>" + data.getOrderItemCustomizations().get(i).getOrderCustomizationDetails().getName() + ": " + "</font> <font color=#292323>" + optionHint + "</font>";
                        holder.adapterOrderItemBinding.txtCustomization2.setText(Html.fromHtml(text));
                    }
                }else if(i==2) {
                    String optionHint="";
                    if(data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().size()>0) {
                        for (int j = 0; j < data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().size();j++){
                            if(optionHint.isEmpty())
                                optionHint=data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().get(j).getOrderCustomizationOptionDetails().getName();
                            else
                                optionHint=optionHint+", "+data.getOrderItemCustomizations().get(i).getOrderItemCustomizationsOptions().get(j).getOrderCustomizationOptionDetails().getName();
                        }
                    }

                    if(!optionHint.isEmpty()) {
                        holder.adapterOrderItemBinding.txtCustomization3.setVisibility(View.VISIBLE);
                        String text = "<font color=#777670>" + data.getOrderItemCustomizations().get(i).getOrderCustomizationDetails().getName() + ": " + "</font> <font color=#292323>" + optionHint + "</font>";
                        holder.adapterOrderItemBinding.txtCustomization3.setText(Html.fromHtml(text));
                    }
                }
            }
        }else{
            holder.adapterOrderItemBinding.txtCustomization1.setVisibility(View.GONE);
            holder.adapterOrderItemBinding.txtCustomization2.setVisibility(View.GONE);
            holder.adapterOrderItemBinding.txtCustomization3.setVisibility(View.GONE);
        }

        if(data.getIn_stock()==0)
            holder.adapterOrderItemBinding.txtOutOfStock.setVisibility(View.VISIBLE);
        else
            holder.adapterOrderItemBinding.txtOutOfStock.setVisibility(View.GONE);

        holder.adapterOrderItemBinding.layLinDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if(Utility.isNetworkAvailable(mContext))
                    itemDoneAPI(position,data.getId());
                else
                    Utility.ShowToast(mContext,mContext.getString(R.string.check_network),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // View holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterOrderItemBinding adapterOrderItemBinding;

        public ViewHolder(AdapterOrderItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.adapterOrderItemBinding = itemBinding;
        }
    }

    // Item done API
    private void itemDoneAPI(final int position, int itemID) {
        try{
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty(API.ORDER_ITEM_ID,itemID);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiInterface.doneOrderItem(jsonObject,Utility.getSharedPreferencesString(mContext, constant.USER_SECURITY_TOKEN));
            new APICall(mContext).Server_Interaction(call, new getResponseData() {
                @Override
                public void onSuccess(JSONObject OBJ, String msg, String typeAPI) {
                    dataList.get(position).setIsPrepared(!dataList.get(position).getIsPrepared());
                    notifyItemChanged(position);
                    ((HomeActivity)mContext).adapter.preparingOrderListFragment.adapter.listNotify(mainItemPosition);
                }

                @Override
                public void onFailed(String msg, String typeAPI) {

                }
            }, API.DONE_ORDER_ITEM);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
