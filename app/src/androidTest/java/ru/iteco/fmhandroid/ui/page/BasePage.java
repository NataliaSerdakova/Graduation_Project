package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import org.hamcrest.Matcher;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import java.util.concurrent.TimeoutException;
import io.qameta.allure.kotlin.Allure;

public class BasePage {

    protected static final int DEFAULT_TIMEOUT = 10000;
    protected static final int SHORT_TIMEOUT = 5000;

    public static ViewAction waitDisplayed(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for view with id <" + viewId + "> displayed during " + millis + " ms.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                Allure.step("Ожидание появления ID: " + viewId);
                uiController.loopMainThreadUntilIdle();
                final long endTime = System.currentTimeMillis() + millis;
                final Matcher<View> matchId = withId(viewId);
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchId.matches(child) && matchDisplayed.matches(child)) {
                            return;
                        }
                    }
                    uiController.loopMainThreadForAtLeast(50);
                } while (System.currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException()).build();
            }
        };
    }

    public static ViewAction waitTextDisplayed(final String text, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for text <" + text + "> during " + millis + " ms.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long endTime = System.currentTimeMillis() + millis;
                final Matcher<View> matchText = withText(text);
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchText.matches(child) && matchDisplayed.matches(child)) return;
                    }
                    uiController.loopMainThreadForAtLeast(50);
                } while (System.currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException()).build();
            }
        };
    }

    public static class ToastMatcher extends TypeSafeMatcher<Root> {
        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if (type == WindowManager.LayoutParams.TYPE_TOAST ||
                    type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                return windowToken != null;
            }
            return false;
        }
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: " + index);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    protected String getTextFromView(Matcher<View> matcher, int index) {
        final String[] text = new String[1];
        onView(withIndex(matcher, index)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text by index";
            }

            @Override
            public void perform(UiController uiController, View view) {
                text[0] = ((TextView) view).getText().toString();
            }
        });
        return text[0];
    }

    public static ViewAction waitTextChange(final Matcher<View> matcher, final String oldText, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for text change from <" + oldText + "> during " + millis + " ms.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                final long endTime = System.currentTimeMillis() + millis;
                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matcher.matches(child)) {
                            String currentText = ((TextView) child).getText().toString();
                            if (!currentText.equals(oldText)) {
                                return;
                            }
                        }
                    }
                    uiController.loopMainThreadForAtLeast(50);
                } while (System.currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException()).build();
            }
        };
    }

    public int getCount(Matcher<View> matcher) {
        final int[] count = {0};
        onView(isRoot()).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "counting views with specific matcher";
            }

            @Override
            public void perform(UiController uiController, View view) {
                for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                    if (matcher.matches(child)) {
                        count[0]++;
                    }
                }
            }
        });
        return count[0];
    }

    public void waitForIdle(long millis) {
        onView(isRoot()).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() { return isRoot(); }
            @Override
            public String getDescription() { return "wait for " + millis + " ms"; }
            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        });
    }
}
