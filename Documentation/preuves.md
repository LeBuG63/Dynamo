# Preuves

#### Je sais utiliser les Intent pour faire communiquer deux activités.

Le fichier GameOverDataBundle implémente Parcelable pour sauvegarder des données 
GameMaster.java instincie ce GameOverDataBundle à la ligne 285

Puis la communication se fait à la ligne 186 du fichier GameActivity.java 
GameOverActivity.java récupère ces données à la ligne 29

#### Je sais développer en utilisant le SDK le plus bas possible.
Nous avons réussi à descendre à 14   


#### Je sais distinguer mes ressources en utilisant les qualifier 
Les images sont dans le dossiers drawable, les fichiers « raw », c’est à dire fichier texte et musiques/sons sont dans le dossier raw, les layout dans les layout etc.

#### Je sais modifier le manifeste de l'application en fonction de mes besoins 
Nous avons du modifier le fichier manifest pour par exemple ajouter des vues et modifier l'orientation de l'écran.

```
< activity android:name=".Activity.GameOverActivity" 
android:screenOrientation="landscape"/>

< activity android:name=".Activity.GameActivity"
android:screenOrientation="landscape"/>

< activity android:name=".Activity.LoadingActivity"
android:screenOrientation="landscape"/>

< activity android:name=".MainActivity"
android:screenOrientation="landscape">

< intent-filter>
< action android:name="android.intent.action.MAIN" />
< category android:name="android.intent.category.LAUNCHER" />
< /intent-filter>
< /activity>
< activity android:name=".Activity$SettingsActivity"
android:parentActivityName=".MainActivity" />
```

Mais aussi pour ajouter un icon et changer le nom de l'apk

```
android:icon="@mipmap/ic_launcher"
android:label="@string/app_name"
android:roundIcon="@mipmap/ic_launcher_round"
```

#### Je sais faire des vues xml en utilisant layouts et composants adéquats 
Nous utilisons beaucoup le constraint layout, qui permet de mettre nos canvas et le timer dans activity_game par exemple, la même situation est présente dans layout_gameover pour mettre en place l'animation de chute avec un canvac et afficher le temps, score, et la distance parcouru

#### Je sais coder proprement mes activités, en m'assurant qu'elles ne font que relayer les évènements 
Par exemple dans GameActivity, les evenements du touchers sont envoyer au GameMaster

```
@Override
public boolean onTouch(View v, MotionEvent event) {
	surfaceView.getRootView().performClick();

	int maskedAction = event.getActionMasked();
	int pointerId = event.getPointerId(event.getActionIndex());

	switch (maskedAction) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_MOVE: {
			MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords
				try {
					event.getPointerCoords(pointerId, pointerCoords);

					fingerPoints[pointerId].x = pointerCoords.x / WindowDefinitions.RESOLUTION_FACTOR;
					fingerPoints[pointerId].y = pointerCoords.y / WindowDefinitions.RESOLUTION_FACTOR;
				} catch (Exception e) {}
				gameManager.setPointsFingerPressed(fingerPoints);
				break;
		}
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP: {
		// permet d eviter une collision accidentel
			fingerPoints[pointerId].x = -1;
			fingerPoints[pointerId].y = -1;

			gameManager.setPointsFingerPressed(fingerPoints);
			break;
		}
	}
	return true;
}
});
```

#### Je sais coder une application en ayant un véritable métier 
Aucune vue n'est présente dans le metier, et plusieurs métiers sont disponibles, le principal étant GameMaster. Mais il y a aussi GameOverMaster

#### Je sais parfaitement séparer vue et modèle 
Les vues sont dans le package Activity, et les modeles dans le package Game. Aucune vue n'est présente à l'intérieur du modèle et l'inverse est vrai aussi. La vue ne fait qu'envoyer les événements au modéle lui correspondant et le modèle traite ces données là.
Comme données, il y'a notemment les événements liés aux chagement de positions des doigts, à savoir si onStop, onPause, onResume sont appelés pour par exemple couper la musique, libérer des ressources etc

#### Je maîtrise le cycle de vie de mon application 
Dans le fichier GameActivty, il y a onPause qui permet de mettre en "pause" le jeu, pour ne pas mettre à les éléments à jour et mettre en pause la musique, onResume qui permet de réprendre la mise à jour des éléments du jeu et remettre la musique. Finalement quand onStop est appelée, on gére la libération de la mémorie et la désintaciations des éléments qui ne servent plus.

```
@Override
public void onStop() {
	super.onStop();
	// si on change d activite, alors celle ci doit finir et le thread du gamemanager doit etre tue pour eviter de surcharger le cpu
	gameManager.stopUpdate();
	gameManager.kill();

	try {
		gameManager.interrupt();
		gameManager.join();
	} catch (InterruptedException ignore) {
	}

	launchLoseActivity(GameMaster.registerDataBundle);
}

@Override
public void onPause() {
	super.onPause();
	instanceOnPause = true;
	gameManager.pauseUpdate();
}

@Override
public void onResume() {
	super.onResume();
	instanceOnPause = false;
	gameManager.resumeUpdate();
}

@Override
public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);

	WindowDefinitions.HEIGHT = WindowUtil.convertDpToPixel(getApplicationContext(), newConfig.screenHeightDp) / WindowDefinitions.RESOLUTION_FACTOR;
	WindowDefinitions.WIDTH = WindowUtil.convertDpToPixel(getApplicationContext(), newConfig.screenWidthDp) / WindowDefinitions.RESOLUTION_FACTOR;

	gameManager.updatePoolPoints();
}
```

#### Je sais utiliser le findViewById à bon escient 
Nous utilisons les findViewById notemment dans GameOverMaster pour mettre à jour les textview permettant d'afficher le temps du joueur, son score et sa distance parcourue 

```
TextView timer = findViewById(R.id.textview_gameover_time);
TextView distance = findViewById(R.id.textview_gameover_distance_player);
TextView score = findViewById(R.id.textview_gameover_score);

.....

timer.setText(dataBundle.getTimer());
distance.setText(String.format("%s%%", Util.roundFloatNDigits(percentage, 2)));
score.setText(String.valueOf(dataBundle.getScore()));
```

#### Je sais gérer les permissions dynamiques de mon application 

Comme notre application est un jeu qui ne demande en rien d'acceder à des fonctions spécifiques du téléphone, nous n'en avons pas utiliser.
Néamoins, pour en ajouter il suffit de mettre dans le fichier manifest, de préférence au début:

```
< uses-permission android:name="android.permission.NOMPERMISSION"/>

```

Pour internet, ce serait:

```
< uses-permission android:name="android.permission.INTERNET"/>

```

Pour vérifier que l'application a bien le droit d'acceder à internet:
```
if (ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
	// l application n'a pas accés à internet
}

```

#### Je sais gérer la persistance légère de mon application 
sharedpreference

#### Je sais gérer la persistance profonde de mon application 
ton systeme de score

En plus du systeme de score, il y'a un niveau qui est inclus de base dans le jeu, dans res/raw/level.txt qui contient toutes les informations du niveau.
Il est chargé grace au LevelLoader qui se trouve dans Game/Level/Loader

#### Je sais afficher une collection de données 

#### Je sais coder mon propre adaptateur 

#### Je maîtrise l'usage des fragments 

#### Je maîtrise l'utilisation de GIT
Nous avons essayés de faire le plus de branches possibles, tout en évitant de push sur le master (sauf de toutes petites modifications testées et retéstées ou pour les fichiers de Documentation)
Nous avons aussi commit à chaque fois dans la bonne branche les modifications apportés, et ceux réguliérement pour avoir un bon suivi de projet et pouvoir revenir quand on le souhaitait à une version antérieur.

