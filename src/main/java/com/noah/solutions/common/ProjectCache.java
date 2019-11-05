package com.noah.solutions.common;

import com.noah.solutions.system.entity.TransferStation;

public class ProjectCache {
    private static TransferStation transferStation;

    public static TransferStation getTransferStation() {
        return transferStation;
    }

    public static void setTransferStation(TransferStation transferStation) {
        ProjectCache.transferStation = transferStation;
    }
}
