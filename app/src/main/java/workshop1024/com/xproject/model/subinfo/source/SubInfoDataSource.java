package workshop1024.com.xproject.model.subinfo.source;

import java.util.List;

import workshop1024.com.xproject.model.subinfo.SubInfo;

public interface SubInfoDataSource {

    void getSubscribeSubInfos(LoadSubInfoCallback loadSubInfoCallback);

    void unSubscribeSubInfoById(String subInfoId);

    void reNameSubscribeSubInfoById(String subInfoId, String newName);

    void getTagSubInfos(LoadSubInfoCallback loadSubInfoCallback);

    void getFilterSubInfos(LoadSubInfoCallback loadSubInfoCallback);

    void markedSubscribeSubInfoesAsRead(List<String> subInfoIdList);

    void markedTagSubInfoesAsRead(List<String> subInfoIdList);

    void markeFilterSubInfoesAsRead(List<String> subInfoIdList);

    interface LoadSubInfoCallback {
        void onSubInfosLoaded(List<SubInfo> subInfoList);

        void onDataNotAvailable();
    }

}
