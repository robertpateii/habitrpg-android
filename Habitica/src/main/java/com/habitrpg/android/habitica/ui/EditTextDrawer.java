package com.habitrpg.android.habitica.ui;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.habitrpg.android.habitica.R;
import com.habitrpg.android.habitica.events.commands.CreateTagCommand;
import com.habitrpg.android.habitica.ui.helpers.ViewHelper;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.model.BasePrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.habitrpg.android.habitica.helpers.TagsHelper;

public class EditTextDrawer extends BasePrimaryDrawerItem<EditTextDrawer, EditTextDrawer.ViewHolder> {
    public EditTextDrawer(TagsHelper tagsHelper) {
        this.tagsHelper = tagsHelper;
    }
    protected static TagsHelper tagsHelper; /// but this is instanced clasS???

    @Override
    public int getType() {
        return R.id.material_drawer_item_primary;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.edit_text_drawer_item;
    }

    @Override
    public void bindView(ViewHolder holder) {
        onPostBindView(this, holder.itemView);
    }

    @Override
    public ViewHolderFactory getFactory() {
        return new ItemFactory();
    }

    public static class ItemFactory implements ViewHolderFactory<EditTextDrawer.ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    public static class ViewHolder extends BaseViewHolder implements View.OnClickListener {

        View view;

        @Bind(R.id.editText)
        EditText editText;

        @Bind(R.id.btnAdd)
        Button btnAdd;

        @Bind(R.id.filterAllHabits)
        RadioButton filterAllHabits;
        @Bind(R.id.filterWeakHabits)
        RadioButton filterWeakHabits;
        @Bind(R.id.filterStrongHabits)
        RadioButton filterStrongHabits;
        @Bind(R.id.filterAllDailies)
        RadioButton filterAllDailies;
        @Bind(R.id.filterDueDailies)
        RadioButton filterDueDailies;
        @Bind(R.id.filterGreyDailies)
        RadioButton filterGreyDailies;
        @Bind(R.id.filterActiveTodos)
        RadioButton filterActiveTodos;
        @Bind(R.id.filterDatedTodos)
        RadioButton filterDatedTodos;
        @Bind(R.id.filterDoneTodos)
        RadioButton filterDoneTodos;

        public void onFilterDoneTodosClicked(View v) {
            // but tags helper isn't static? or can't be???
            tagsHelper.filterDone = true;
            /* ok now what??
            i think i messed up i should be using a listbox preference instead of my
            custom radio buttons that do nothing.
            How else am I to get the fact that a filter is on over to HabitItemRecyclerViewAdapter.filter()?
            Preference foo = new CheckBoxPreference()??
            this is how toshevski did it
            https://github.com/HabitRPG/habitrpg-android/pull/438/commits/154bd8c1d255e437b6af77097d1f5f2c7569d4d2
            However that puts it in the preference drawer... which is not what vIiRuS wants...
            so i need to ...add a second preference drawer? add something other than preferences to share the state like tags helper?
            or extend tags helper to handle the filters?
            so when this class is instantiated   new EditTextDrawer() tagsHelper is in that context
            so i could pass the reference into here
            then update the values on it based on the radio buttons
            then add the logic for each filter to TagsHelper?
            Should i make a FilterHelper instead of TagsHelper? it's probably better from an OO standpoint but i'm still new to this
             */

        }


        private ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);

            ViewHelper.SetBackgroundTint(btnAdd, ContextCompat.getColor(view.getContext(), R.color.brand));

            // does this mean clicking the button fires the onClick for the entire view? Which is the public void onClick below?
            btnAdd.setOnClickListener(this);
            filterDoneTodos.setOnClickListener(this::onFilterDoneTodosClicked);
            filterActiveTodos.setOnClickListener(this::onFilterDoneTodosClicked);
            filterDatedTodos.setOnClickListener(this::onFilterDoneTodosClicked);
        }

        @Override
        public void onClick(View v) {
            String text = editText.getText().toString();

            if (text.equals(""))
                return;

            EventBus.getDefault().post(new CreateTagCommand(editText.getText().toString()));

            editText.setText("");
        }
    }
}
