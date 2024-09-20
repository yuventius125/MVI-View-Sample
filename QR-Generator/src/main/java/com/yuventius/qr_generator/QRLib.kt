package com.yuventius.qr_generator

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.createQrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape

object QRLib {
    val SECRET_KEY = "wFkjUve2cPaqn0zAR3milt41UZntbGXk"

    fun makeQRImage(value: String, logoDrawable: Drawable? = null): Drawable {
        val data = QrData.Url(value)

        return QrCodeDrawable(data)

//        val options = createQrVectorOptions {
//            padding = .125f
//
//            background {
//                color = QrVectorColor.Solid(Color.WHITE)
//            }
//
//            logoDrawable?.let {
//                logo {
//                    drawable = it
//                    padding = QrVectorLogoPadding.Accurate(.1f)
//                    shape = QrVectorLogoShape.Circle
//                    colors {
//                        backgroundColor = QrVectorColor.LinearGradient(
//                            colors = listOf(
//                                0f to Color.CYAN,
//                                1f to Color.BLUE
//                            ),
//                            orientation = QrVectorColor.LinearGradient.Orientation.LeftDiagonal
//                        )
//                    }
//                }
//            }
//
//            colors {
//                dark = QrVectorColor.LinearGradient(
//                    colors = listOf(
//                        0f to Color.CYAN,
//                        1f to Color.BLUE
//                    ),
//                    orientation = QrVectorColor.LinearGradient.Orientation.LeftDiagonal
//                )
//                ball = QrVectorColor.LinearGradient(
//                    colors = listOf(
//                        0f to Color.CYAN,
//                        1f to Color.BLUE
//                    ),
//                    orientation = QrVectorColor.LinearGradient.Orientation.LeftDiagonal
//                )
//                frame = QrVectorColor.LinearGradient(
//                    colors = listOf(
//                        0f to Color.CYAN,
//                        1f to Color.BLUE
//                    ),
//                    orientation = QrVectorColor.LinearGradient.Orientation.LeftDiagonal
//                )
//            }
//
//            shapes {
//                darkPixel = QrVectorPixelShape.Circle()
//                ball = QrVectorBallShape.Circle()
//                frame = QrVectorFrameShape.Circle()
//            }
//        }
//
//        return QrCodeDrawable(data, options)
    }
}