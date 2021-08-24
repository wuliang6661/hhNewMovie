package com.myp.hhcinema.ui.moviesseattable;


import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.api.HttpServiceIml;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.aCinemaSeatTableBO;
import com.myp.hhcinema.entity.preferentialnumberBo;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SeatTablePresenter extends BasePresenterImpl<SeatTableContract.View>
        implements SeatTableContract.Presenter {
    @Override
    public void getsets(String cinemaId, String dxId) {

        HttpInterfaceIml.getsets(cinemaId, dxId).
                subscribe(new Subscriber<preferentialnumberBo>() {
                    @Override
                    public void onCompleted() {
                        if (mView == null)
                            return;
                        mView.onRequestEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView == null)
                            return;
                        mView.onRequestError(e.getMessage());
                    }

                    @Override
                    public void onNext(preferentialnumberBo s) {
                        if (mView == null)
                            return;
                        mView.getpreferentialnumberBo(s);
                    }
                });
    }

    @Override
    public void lockSeats(String orderName, String seats, String seatId, String ticketOriginPrice, String ticketNum,
                          String cinemaNumber, String hallId, String playId, String cineMovieNum,String preferPrice,
                          String globalPreferPrice,String globalCanBuyNum) {
        HttpInterfaceIml.lockSeats(orderName, seats,seatId,ticketOriginPrice,ticketNum,cinemaNumber,hallId,
                playId,cineMovieNum,preferPrice,globalPreferPrice,globalCanBuyNum).
                subscribe(new Subscriber<LockSeatsBO>() {
                    @Override
                    public void onCompleted() {
                        if (mView == null)
                            return;
                        mView.onRequestEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView == null)
                            return;
                        mView.onRequestError(e.getMessage());
                    }

                    @Override
                    public void onNext(LockSeatsBO s) {
                        if (mView == null)
                            return;
                        mView.getConfirmOrderInfo(s);
                    }
                });
    }

    @Override
    public void loadSeats(String cinemaId, String playId) {
        HttpInterfaceIml.getSeats(cinemaId, playId).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        if (mView == null)
                            return;
                        mView.onRequestEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView == null)
                            return;
                        mView.onRequestError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (mView == null)
                            return;
                        try {
                            mView.getSeats(responseBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void loadSeatTables(String cid, String playId, String updateTime) {

        HttpServiceIml.getCinemasSeatStatus(cid, playId, updateTime).
                subscribe(new Subscriber<List<aCinemaSeatTableBO>>() {
                    @Override
                    public void onCompleted() {
                        if (mView == null)
                            return;
                        mView.onRequestEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView == null)
                            return;
                        mView.onRequestError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<aCinemaSeatTableBO> s) {
                        if (mView == null)
                            return;
                        mView.getSeatData(s);
                    }
                });
    }


}
