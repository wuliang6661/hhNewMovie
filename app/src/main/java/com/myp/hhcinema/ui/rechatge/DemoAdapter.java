package com.myp.hhcinema.ui.rechatge;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myp.hhcinema.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/27.
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.BaseViewHolder> {
    private ArrayList<ItemModel> dataList = new ArrayList<>();
    private int lastPressIndex = -1;
    private Context mContext;
    private boolean type;

    public void replaceAll(ArrayList<ItemModel> list, Context context) {
        mContext = context;
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public DemoAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case ItemModel.ONE:
                ///左上角三角标识布局；
                return new CornorViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout.corner_one, parent, false));
            case ItemModel.TWO:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false));

            case ItemModel.THREE:
                return new TWoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.two, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DemoAdapter.BaseViewHolder holder, int position) {

        holder.setData(dataList.get(position).data,dataList.get(position).payPrice);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object data,Object payPrice) {
        }
    }

    private class OneViewHolder extends BaseViewHolder {
        private TextView tv;
        private RelativeLayout re;
        private TextView texta;
        private TextView txtActivity;
        public OneViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            texta = (TextView) view.findViewById(R.id.text);
            re = (RelativeLayout) view.findViewById(R.id.re);
            txtActivity = view.findViewById(R.id.txtActivity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notyfy = 0;
                    int position = getAdapterPosition();
                    ItemModel model1 = dataList.get(position);
                    ItemModel model = new ItemModel(model1.type, model1.data,model1.payPrice, model1.dataa,true);
                    Log.e("model", "OneViewHolder: " + model.type);
                    Log.e("model", "OneViewHolder: " + model.data);
                    Log.e("model", "OneViewHolder: " + model.dataa);
                    Log.e("model", "OneViewHolder: " + model.isFree);
                    type=true;
                    EventBus.getDefault().post(model);
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    notifyDataSetChanged();


                }

            });
        }

        @Override
        void setData(Object data,Object payPrice) {
            if (data != null) {
                String text = (String) data;
                String payGet = (String) payPrice;
                tv.setText(text);
                double xx = Math.floor(Float.parseFloat(text));
                double xxx = Math.floor(Float.parseFloat(payGet));
                if (xxx - xx >0){
                    String xxxx = xxx - xx + "";
                    if (xxxx.indexOf(".") > 0) {
                        xxxx = xxxx.replaceAll("0+?$", "");//去掉多余的0
                        xxxx = xxxx.replaceAll("[.]$", "");//如最后一位是.则去掉
                    }
                    txtActivity.setVisibility(View.VISIBLE);
                    txtActivity.setText(String.format("赠送%s元",xxxx));
                }else {
                    txtActivity.setVisibility(View.GONE);
                }

                if (getAdapterPosition() == lastPressIndex) {
                    tv.setSelected(true);
                    tv.setTextColor(Color.parseColor("#e7271c"));
                    re.setBackgroundResource(R.drawable.jinexuanzhong);
                    texta.setTextColor(Color.parseColor("#e7271c"));
                    txtActivity.setTextColor(Color.parseColor("#e7271c"));
                } else {
                    tv.setSelected(false);
                    tv.setTextColor(Color.parseColor("#4c4c4c"));
                    texta.setTextColor(Color.parseColor("#4c4c4c"));
                    re.setBackgroundResource(R.drawable.jinemoren);
                    txtActivity.setTextColor(Color.parseColor("#4c4c4c"));

                }

            }


        }
    }

    int notyfy = 0;

    private class TWoViewHolder extends BaseViewHolder {
        private EditText et;
        private String chargeFunds;

        public TWoViewHolder(View view) {
            super(view);

            et = (EditText) view.findViewById(R.id.et);
            final int position = getAdapterPosition();
            //ItemModel model = dataList.get(position);
            //Log.e("TWoViewHolder", "TWoViewHolder: "+model.toString());
            Log.e("TWoViewHolder", "TWoViewHolder: ");
            et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                 et.setText("");
                    type=false;
                    if (hasFocus) {

                        if (lastPressIndex != position) {
                            notifyItemChanged(lastPressIndex);
                            lastPressIndex = position;
                        }
                    }
                }
            });
            et.addTextChangedListener(new TextWatcher() {


                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() > 0) {
                        String inputText = et.getText().toString().trim();

                            chargeFunds = inputText;

                    }
                    if(type==false){
                        String funds = chargeFunds ;
                        ItemModel model = new ItemModel(ItemModel.THREE, funds, "","qita",false);
                        Log.e("model", "OneViewHolder: " + model.type);
                        Log.e("model", "OneViewHolder: " + model.data);
                        Log.e("model", "OneViewHolder: " + model.dataa);
                        Log.e("model", "OneViewHolder: " + model.isFree);

                        EventBus.getDefault().post(model);
                    }



                }
            });


        }


        @Override
        void setData(Object data,Object payPrice) {

            super.setData(data,payPrice);
            final int position = getAdapterPosition();
            if (position == lastPressIndex)
                et.requestFocus();
            else
                et.clearFocus();

        }
    }

    private class CornorViewHoler extends BaseViewHolder {
        private TextView tv;

        public CornorViewHoler(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            //int position = getAdapterPosition();
            //ItemModel model = dataList.get(position);
            //Log.e("TAG", "OneViewHolder: "+model.toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "OneViewHolder: ");
                    notyfy = 0;
                    int position = getAdapterPosition();
                    ItemModel model = dataList.get(position);
                    EventBus.getDefault().post(model);
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    notifyDataSetChanged();


                }

            });
        }

        @Override
        void setData(Object data,Object payPrice) {
            if (data != null) {
                String text = (String) data;
                tv.setText(text);
                if (getAdapterPosition() == lastPressIndex) {
                    tv.setSelected(true);
                    tv.setTextColor(Color.parseColor("#3FE2C5"));

                } else {
                    tv.setSelected(false);
                    tv.setTextColor(Color.parseColor("#4c4c4c"));
                }

            }
        }
    }
}