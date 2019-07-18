package com.lyzb.jbx.dbdata;

import com.lyzb.jbx.dbdata.manger.DataBaseManager;
import com.lyzb.jbx.model.search.HistroyModel;
import com.lyzb.jbx.model.search.HistroyModelDao;

import java.util.List;

/**
 * Created by shidengzhong on 2019/3/4.
 */

public class SearchHistroyDb {
    private HistroyModelDao modelDao;

    private SearchHistroyDb() {
        modelDao = DataBaseManager.getIntance().getHistroyModelDao();
    }

    private static final class Holder {
        private static final SearchHistroyDb INSTANCE = new SearchHistroyDb();
    }

    public static SearchHistroyDb getIntance() {
        return SearchHistroyDb.Holder.INSTANCE;
    }

    public void insert(HistroyModel model) {
        if (model!=null){
            model.setValue(model.getValue().trim());
        }
        HistroyModel dataModel =modelDao.queryBuilder().where(HistroyModelDao.Properties.Value.eq(model.getValue())).unique();
        if (dataModel==null){
            modelDao.insert(model);
        }else {
            model.setId(dataModel.getId());
            modelDao.insertOrReplace(model);
        }
    }

    /**
     * 倒叙查询10条数据
     * @return
     */
    public List<HistroyModel> getHistroyList(){
        return modelDao.queryBuilder()
                .orderDesc(HistroyModelDao.Properties.Time)
                .limit(10)
                .list();
    }
}
