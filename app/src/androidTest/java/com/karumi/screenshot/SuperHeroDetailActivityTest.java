package com.karumi.screenshot;


import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.karumi.screenshot.di.MainComponent;
import com.karumi.screenshot.di.MainModule;
import com.karumi.screenshot.model.SuperHero;
import com.karumi.screenshot.model.SuperHeroesRepository;
import com.karumi.screenshot.ui.view.SuperHeroDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class SuperHeroDetailActivityTest extends ScreenshotTest {

    @Rule
    public DaggerMockRule<MainComponent> daggerRule =
            new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
                    new DaggerMockRule.ComponentSetter<MainComponent>() {
                        @Override public void setComponent(MainComponent component) {
                            SuperHeroesApplication app =
                                    (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();
                            app.setComponent(component);
                        }
                    });

    @Rule public IntentsTestRule<SuperHeroDetailActivity> activityRule =
            new IntentsTestRule<>(SuperHeroDetailActivity.class, true, false);

    @Mock
    SuperHeroesRepository repository;

    @Test
    public void showsRegularSuperHero() {
        SuperHero superHero = givenASuperHero();

        Activity activity = startActivity(generateIntentWithName(superHero.getName()));

        compareScreenshot(activity);
    }

    @Test
    public void showsAvengerSuperHero() {
        SuperHero superHero = givenAnAvenger();

        Activity activity = startActivity(generateIntentWithName(superHero.getName()));

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroWithLongName() {
        SuperHero superHero = givenASuperHeroWithALongName();

        Activity activity = startActivity(generateIntentWithName(superHero.getName()));

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroWithLongDescription() {
        SuperHero superHero = givenASuperHeroWithALongDescription();

        Activity activity = startActivity(generateIntentWithName(superHero.getName()));

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroWithEmptyName() {
        SuperHero superHero = givenASuperHero("", "Empty name description", false);

        Activity activity = startActivity(generateIntentWithName(superHero.getName()));

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroWithEmptyDescription() {
        SuperHero superHero = givenASuperHero("Name", "", false);

        Activity activity = startActivity(generateIntentWithName(superHero.getName()));

        compareScreenshot(activity);
    }

    private void givenThereAreNoSuperHeroes() {
        when(repository.getAll()).thenReturn(Collections.<SuperHero>emptyList());
    }

    private SuperHero givenASuperHeroWithALongDescription() {
        String superHeroName = "Super Hero Name";
        String superHeroDescription =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
                        + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
                        + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
                        + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
                        + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt "
                        + "mollit anim id est laborum.";
        boolean isAvenger = false;
        return givenASuperHero(superHeroName, superHeroDescription, isAvenger);
    }

    private SuperHero givenASuperHeroWithALongName() {
        String superHeroName =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
                        + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
                        + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
                        + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
                        + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt "
                        + "mollit anim id est laborum.";
        String superHeroDescription = "Description Super Hero";
        boolean isAvenger = false;

        SuperHero superHero = givenASuperHero(superHeroName, superHeroDescription, isAvenger);

        return superHero;
    }

    private SuperHero givenAnAvenger() {
        SuperHero superHero = givenASuperHero("Super Hero Name", "Super Hero Description", true);

        when(repository.getByName(anyString())).thenReturn(superHero);

        return superHero;
    }

    private SuperHero givenASuperHero() {
        SuperHero superHero = givenASuperHero("Super Hero Name", "Super Hero Description", false);

        when(repository.getByName(anyString())).thenReturn(superHero);

        return superHero;
    }

    private SuperHero givenASuperHero(String superHeroName, String superHeroDescription,
                                      boolean isAvenger) {
        SuperHero superHero = getASuperHero(superHeroName, superHeroDescription, isAvenger);

        when(repository.getByName(anyString())).thenReturn(superHero);

        return superHero;
    }

    private SuperHero getASuperHero(String superHeroName, String superHeroDescription,
                                      boolean isAvenger) {
        return new SuperHero(superHeroName, null, isAvenger, superHeroDescription);
    }

    private SuperHeroDetailActivity startActivity(Intent intent) {
        return activityRule.launchActivity(intent);
    }

    private Intent generateIntentWithName(String name){
        Intent nameIntent = new Intent();
        nameIntent.putExtra(SuperHeroDetailActivity.SUPER_HERO_NAME_KEY, name);
        return nameIntent;
    }
}
