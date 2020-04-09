package com.example.aplikasiuntukuts.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CheeseRepository {
    private CheeseDao mCheeseDao;
    private LiveData<List<Cheese>> mAllCheese;

    CheeseRepository(Application application) {
        SampleDatabase db = SampleDatabase.getInstance(application);
        mCheeseDao = db.cheese();
        mAllCheese = mCheeseDao.getAllCheeses();
    }
    LiveData<List<Cheese>> getAllCheese() {
        return mAllCheese;
    }
    public void insert (Cheese cheese) {
        new insertAsyncTask((CheeseDao) mAllCheese).execute(cheese);
    }
    private static class insertAsyncTask extends AsyncTask<Cheese, Void, Void> {

        private CheeseDao mAsyncTaskDao;

        insertAsyncTask(CheeseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cheese... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteCheeseAsyncTask extends AsyncTask<Cheese, Void, Void> {
        private CheeseDao mAsyncTaskDao;

        deleteCheeseAsyncTask(CheeseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cheese... params) {
            mAsyncTaskDao.deleteCheese (params[0]);
            return null;
        }
    }

    public void deleteCheese(Cheese cheese)  {
        new deleteCheeseAsyncTask(mCheeseDao).execute(cheese);
    }

}
