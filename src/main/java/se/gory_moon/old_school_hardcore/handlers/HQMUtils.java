package se.gory_moon.old_school_hardcore.handlers;


/**
 * Class to do soft dependency on HQM
 */
public class HQMUtils {

    public static boolean isHQMHardcoreActive() {
        return false;
        //return QuestingDataManager.getInstance().isQuestActive() &&
        //        QuestingDataManager.getInstance().isHardcoreActive();
    }

}
