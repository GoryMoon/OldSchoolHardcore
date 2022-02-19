package se.gory_moon.old_school_hardcore.handlers;

import hardcorequesting.common.forge.quests.QuestingDataManager;

/**
 * Class to do soft dependency on HQM
 */
public class HQMUtils {

    public static boolean isHQMHardcoreActive() {
        return QuestingDataManager.getInstance().isQuestActive() &&
                QuestingDataManager.getInstance().isHardcoreActive();
    }

}
