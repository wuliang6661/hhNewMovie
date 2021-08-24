package com.myp.hhcinema.ui.moviesseattable;

import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.aCinemaSeatTableBO;
import com.myp.hhcinema.entity.preferentialnumberBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SeatTableContract {
    interface View extends BaseRequestView {

        void getSeatData(List<aCinemaSeatTableBO> s);
        void getpreferentialnumberBo(preferentialnumberBo s);

        void getConfirmOrderInfo(LockSeatsBO lockSeatsBO);

        void getSeats(ResponseBody responseBody) throws IOException, JSONException;
    }

    interface Presenter extends BasePresenter<View> {
        void loadSeatTables(String cid, String playId, String updateTime);
        void getsets(String cinemaId, String dxId);

        /**
         * 提交订单
         * @param orderName
         * @param seats
         * @param seatId
         * @param ticketOriginPrice
         * @param ticketNum
         * @param cinemaNumber
         * @param hallId
         * @param playId
         * @param cineMovieNum
         */
        void lockSeats(String orderName, String seats, String seatId, String ticketOriginPrice, String ticketNum,
                       String cinemaNumber, String hallId, String playId, String cineMovieNum,String preferPrice,
                       String globalPreferPrice,String globalCanBuyNum);


        void loadSeats(String cinemaId,String playId);
    }
}
