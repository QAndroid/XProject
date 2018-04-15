package workshop1024.com.xproject.model.sub.source;

import java.util.List;

import workshop1024.com.xproject.model.sub.SubInfo;

public interface SubInfoDataSource {

    void getSubscribeSubInfos(LoadSubInfoCallback loadSubInfoCallback);

    void unSubscribeSubInfoById(String subInfoId);

    void reNameSubscribeSubInfoById(String subInfoId, String newName);

    void getTagSubInfos(LoadSubInfoCallback loadSubInfoCallback);

    void getFilterSubInfos(LoadSubInfoCallback loadSubInfoCallback);

    interface LoadSubInfoCallback {
        void onSubInfosLoaded(List<SubInfo> subInfoList);

        void onDataNotAvailable();
    }

}
