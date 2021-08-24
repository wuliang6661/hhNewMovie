package com.myp.hhcinema.ui.moviesmessage;

import com.myp.hhcinema.entity.CriticBO;
import com.myp.hhcinema.entity.MoviesCommentBO;
import com.myp.hhcinema.entity.ShareBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MoviesMessageContract {
    interface View extends BaseRequestView {

        void getMoviesComment(MoviesCommentBO moviesCommentBO);

        void getShareMessage(ShareBO shareBO);


        void getCritic(List<CriticBO> criticBO);
        void getDianZan(CriticBO criticBO,int position);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取单个电影影评
         */
        void loadMoviesComment(String appUserId, String movieId);

        /**
         * 收藏电影
         */
        void loadMoviesShouCang(String appUserId, String movieId, boolean isShouCang);

        /**
         * 想看电影
         */
        void loadMoviesWantSee(String appUserId, String movieId, boolean isWantSee);


        /**
         * 分享电影
         */
        void loadShareMovie(String movieId);
        /**
         * 评论电影
         */
        void loadMoviesCritic(Long movieId);


        void loadDianZan(Long id,int position);

    }
}
