package cn.hachin.hfuthelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cn.hachin.hfuthelper.fragment.FragmentSchedule;
import cn.hachin.hfuthelper.fragment.MenuFragment;


public class MainActivity extends SlidingFragmentActivity {
    SlidingMenu sm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.menu);
        setContentView(R.layout.content);
        Fragment f = new FragmentSchedule();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();


        //1.得到滑动菜单
        sm = getSlidingMenu();
        //2.设置滑动菜单左边有还是右边有
        sm.setMode(SlidingMenu.LEFT);
        //3.设置滑动菜单出来后 内容页剩余宽度
        sm.setBehindWidthRes(R.dimen.slidingmenu_offset);
        //4.设置阴影
        sm.setShadowDrawable(R.drawable.shadow);
        //5.设置阴影的宽度
        sm.setShadowWidth(R.dimen.shadow_width);
        //6.设置滑动范围
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);


        //创建fragment
        MenuFragment menuFragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menuFragment, "Menu").commit();

    }

    public void switchFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
        sm.toggle();
        /*
          public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, R.anim.slide_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }

         */
    }

}
