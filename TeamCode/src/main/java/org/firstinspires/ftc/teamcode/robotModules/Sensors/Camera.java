package org.firstinspires.ftc.teamcode.robotModules.Sensors;

/**
 * В этом классе описывается метод работы с камерой для распознования командного реквизита.
 * По правилам сезона Centerstage реквизит распологается на одной из трех позиций относительно робота.
 * Чтобы выполнить задание рандомизации в автономном периоде используется камера и она передает данные
 * куда надо проехать и положить желтый пиксель.
 */

public class Camera {
    /*
    TODO:   создать объект камеры
            описать метод просмотра информации с камеры
            описать области сканирования
            описать метод получения данных с камеры
    */
    /*
    на практике должно выглядеть, как:
    Camera cam = new Camera();
    cam.init(webcam, 1920, 1080)
    (в ран оп моде)
    while(!isStarted()){
        cam.detect();
        telemetry.addData("position: ", cam.detectionTelemetry());
        telemetry.update();
    }
    if(opModeIsActive()){
        cam.detect();
        telemetry.addData("ME GO ", cam.detectionTelemetry());
        telemetry.update();
        //езда, сброс и т.д.
    }
     */

}
