package org.firstinspires.ftc.teamcode.notUsed_trash;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored.DriveEncoder;

/**
 * Это автоном, с помощью которого можно проверить, как работает езда по энкодерам
 * но сначала надо понять, а работают ли они вообще...
 *
 * поэтому тут тестить сначала oneSpin, потом driveBy1Encoder, потом (если заработает) driveBy4Encoder
 *
 * комментарием "//.комментарий" помечено то, что добавить в используемый автоном обязательно, если этот сработает
 */

@Autonomous(name = "one spin", group = "alfa")
public class AOneSpin extends LinearOpMode {
    DriveEncoder encodedTrain = null; //.это обязательно
    //ну тут можно объявить какой угодно дополнительный модуль

    @Override
    public void runOpMode(){
        encodedTrain = new DriveEncoder(this);
        encodedTrain.initDE();

        if(opModeIsActive()) {
            encodedTrain.oneSpin(encodedTrain.getTR());//TODO: сначала тестить это
            //encodedTrain.driveBy1Endcoder(encodedTrain.getTL(), 0.5, 0, 0, 100); //TODO: потом это
            //  //encodedTrain.driveBy4Encoder(0.5, 0, 0, 100); //TODO: и потом вот это

            //не забываем добавить вывод отладочной информации в телеметрию
        }
    }
}
