# GradientImageView

**apply Gradient to ImageView**

## Preview

<div>
<img src="https://github.com/SSong-develop/GradientImageView/blob/main/art/preview1.png" width="300" height="650"/>
</div>

## How To Use

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.ssong_develop.lib.GradientImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/ic_launcher_foreground"
        app:gradient_start_color="@color/teal_200"
        app:gradient_end_color="@color/black"
        app:corner_radius="12dp"
        app:gradient_alpha="0.7"
        app:gradient_direction="right_to_left"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## attributes

- gradient_start_color
- gradient_end_color
- corner_radius
- gradient_direction
- gradient_alpha