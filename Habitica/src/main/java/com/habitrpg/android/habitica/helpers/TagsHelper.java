package com.habitrpg.android.habitica.helpers;

import android.util.Log;

import com.magicmicky.habitrpgwrapper.lib.models.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magicmicky on 02/10/15.
 */
public class TagsHelper {
    private List<String> tagsId;

    public TagsHelper() {
        tagsId = new ArrayList<String>();

    }

    public void setTags(List<String> tagsId) {
        this.tagsId = tagsId;
    }

    public void addTags(String tags) {
        this.tagsId.add(tags);
    }

    public int howMany() {
        return this.tagsId.size();
    }

    public List<String> getTags() {
        return this.tagsId;
    }

    public boolean isTagChecked(String tagID) {
        return this.tagsId.contains(tagID);
    }

    public List<Task> filter(List<Task> tasks) {
        List<Task> filtered = new ArrayList<Task>();
        List<Task> extraFiltered = new ArrayList<Task>();
        for (Task t : tasks) {
            // take all tasks and add the ones that match all the tags in tagsId to filtered list.
            if (t.containsAllTagIds(this.tagsId)) {
                filtered.add(t);
            }
        }
        // input tasks matching selected tags
        // output input less tasks that do not match selected filters
        for (Task fTask : filtered) {
            if (matchesFilter(fTask)) {
                extraFiltered.add(fTask);
            }
        }
        return extraFiltered;
    }

    public boolean filterDone;

    private boolean matchesFilter(Task task) {
        // check values set in this class by EditTextDrawer to see which filters to execute?
        // i can probably get the filter logic from the website code
        /*vliRuS:
            weak habits are all habits with a value <= 0. Strong habits are all with a value > 0
            dated are all todos, that are not completed and have a duedate set. Done are all completed todos
            grey for dailies is the inverse of the due dailies
         */
        if (filterDone) {
            // ??
        }
        return true;
    }

    public List<Task> filterDue(List<Task> tasks, int offset) {
        if (tasks.size() > 0 && !tasks.get(0).getType().equals(Task.TYPE_DAILY)) return tasks;
        List<Task> filtered = new ArrayList<Task>();
        for (Task t : tasks) {
            if (t.getType().equals(Task.TYPE_DAILY))
            Log.i("MT:", "Task is due - " + t.isDisplayedActive(offset) + " N: " + t.getText());
            if (t.isDisplayedActive(offset))
                filtered.add(t);
        }
        return filtered;
    }
}
