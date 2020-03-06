package com.Se4Med.OKO;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class svolgimentoTest_test {

    @Rule
    public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

    @Test
    public void svolgimentoTest_test() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("erica@unibg.it"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("erica"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_login), withText("LOGIN"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.listBox),
                        childAtPosition(
                                withId(R.id.container),
                                1)))
                .atPosition(1);
        checkedTextView.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.button_login), withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        button.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.guidelines), withText("Go to the Guidelines"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.Title), withText("Guidelines"),
                        childAtPosition(
                                allOf(withId(R.id.Title_container),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Guidelines")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.go_test), withText("Start the test"),
                        childAtPosition(
                                allOf(withId(R.id.test_container),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                1),
                        isDisplayed()));
        button2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageView = onView(
                allOf(withId(R.id.cland),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                1),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.left_1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                0),
                        isDisplayed()));
        imageView2.perform(click());

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.left_1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                0),
                        isDisplayed()));
        imageView3.perform(click());

        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView4.perform(click());

        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.left_1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                0),
                        isDisplayed()));
        imageView5.perform(click());

        ViewInteraction imageView6 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView6.perform(click());

        ViewInteraction imageView7 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView7.perform(click());

        ViewInteraction imageView8 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView8.perform(click());

        ViewInteraction imageView9 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView9.perform(click());

        ViewInteraction imageView10 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView10.perform(click());

        ViewInteraction imageView11 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView11.perform(click());

        ViewInteraction imageView12 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView12.perform(click());

        ViewInteraction imageView13 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView13.perform(click());

        ViewInteraction imageView14 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView14.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView2), withText("Thanks!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Thanks!")));

        ViewInteraction imageView15 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView15.perform(click());

        ViewInteraction imageView16 = onView(
                allOf(withId(R.id.left_1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                0),
                        isDisplayed()));
        imageView16.perform(click());

        ViewInteraction imageView17 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView17.perform(click());

        ViewInteraction imageView18 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView18.perform(click());

        ViewInteraction imageView19 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView19.perform(click());

        ViewInteraction imageView20 = onView(
                allOf(withId(R.id.up),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        0),
                                0),
                        isDisplayed()));
        imageView20.perform(click());

        ViewInteraction imageView21 = onView(
                allOf(withId(R.id.up),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        0),
                                0),
                        isDisplayed()));
        imageView21.perform(click());

        ViewInteraction imageView22 = onView(
                allOf(withId(R.id.up),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        0),
                                0),
                        isDisplayed()));
        imageView22.perform(click());

        ViewInteraction imageView23 = onView(
                allOf(withId(R.id.up),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        0),
                                0),
                        isDisplayed()));
        imageView23.perform(click());

        ViewInteraction imageView24 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView24.perform(click());

        ViewInteraction imageView25 = onView(
                allOf(withId(R.id.up),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        0),
                                0),
                        isDisplayed()));
        imageView25.perform(click());

        ViewInteraction imageView26 = onView(
                allOf(withId(R.id.right),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        1),
                                2),
                        isDisplayed()));
        imageView26.perform(click());

        ViewInteraction imageView27 = onView(
                allOf(withId(R.id.down),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.test_container),
                                        2),
                                0),
                        isDisplayed()));
        imageView27.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.res), withText("Your result"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Your result")));

        ViewInteraction imageView28 = onView(
                allOf(withId(R.id.image_ris),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        imageView28.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
