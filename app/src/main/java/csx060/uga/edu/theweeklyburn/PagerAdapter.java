/**
 * Pager Adapter
 * @authors: Jeffrey Kao & Michael Tseng
 * Controls the way the view pager works
 */

package csx060.uga.edu.theweeklyburn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Sets the number of tabs for the view pager
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    /**
     * Gets the tab corresponding to the page the user is on
     * @param position - page number
     * @return - the fragment corresponding to that page
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DailyChallengeFragment tab1 = new DailyChallengeFragment();
                return tab1;
            case 1:
                FriendsFragment tab2 = new FriendsFragment();
                return tab2;
            case 2:
                ProfileFragment tab3 = new ProfileFragment();
                return tab3;
            default:
                return null;
        }
    }

    /**
     * Gets the total number of tabs
     * @return - the number of tabs
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
