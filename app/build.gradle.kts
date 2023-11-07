plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	//id("org.jetbrains.kotlin.kapt")
	id("com.google.devtools.ksp")
	// Add the Google services Gradle plugin
	id("com.google.gms.google-services")
	id("com.google.firebase.crashlytics")

}

android {
	namespace = "com.edu.uniandes.fud"
	compileSdk = 33
	
	defaultConfig {
		applicationId = "com.edu.uniandes.fud"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
		configurations.all {
			resolutionStrategy {
				force("androidx.emoji2:emoji2-views-helper:1.3.0")
				force("androidx.emoji2:emoji2:1.3.0")
			}
		}
		javaCompileOptions {
			annotationProcessorOptions {
				arguments += mapOf(
					"room.schemaLocation" to "$projectDir/schemas",
					"room.incremental" to "true"
				)
			}
		}

	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.4.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}



dependencies {

	implementation("com.google.firebase:firebase-crashlytics-ktx")
	implementation("com.google.firebase:firebase-analytics-ktx")

	implementation("io.coil-kt:coil-compose:2.4.0")
	
	implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
	implementation("com.google.firebase:firebase-database-ktx:20.2.2")
	implementation("com.google.firebase:firebase-firestore-ktx")
	implementation("com.google.firebase:firebase-analytics-ktx")
	
	implementation("com.google.android.gms:play-services-location:21.0.1")

	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
	implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
	implementation("com.squareup.moshi:moshi:1.13.0")
	implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
	implementation("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.7.2")
	implementation(platform("androidx.compose:compose-bom:2023.03.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.compose.runtime:runtime-livedata:1.5.2")
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
	implementation("androidx.compose.ui:ui-text-android:1.5.2")
	
	val room_version = "2.5.0"
	implementation("androidx.room:room-runtime:$room_version")
	annotationProcessor("androidx.room:room-compiler:$room_version")


	// To use Kotlin annotation processing tool (kapt)
	//kapt("androidx.room:room-compiler:$room_version")
	// To use Kotlin Symbol Processing (KSP)
	ksp("androidx.room:room-compiler:$room_version")

	// optional - Kotlin Extensions and Coroutines support for Room
	implementation("androidx.room:room-ktx:$room_version")

	// optional - RxJava2 support for Room
	implementation("androidx.room:room-rxjava2:$room_version")

	// optional - RxJava3 support for Room
	implementation("androidx.room:room-rxjava3:$room_version")

	// optional - Guava support for Room, including Optional and ListenableFuture
	implementation("androidx.room:room-guava:$room_version")

	// optional - Test helpers
	testImplementation("androidx.room:room-testing:$room_version")

	// optional - Paging 3 Integration
	implementation("androidx.room:room-paging:$room_version")

    testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")


}