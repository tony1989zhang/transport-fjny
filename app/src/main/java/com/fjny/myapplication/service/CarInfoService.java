package com.fjny.myapplication.service;

import android.util.Xml;

import com.fjny.myapplication.model.CarInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarInfoService {
    //通过xml文件获取数据
    public static List<CarInfo> getFormXML(InputStream is) throws XmlPullParserException, IOException {
        List<CarInfo> list = null;
        CarInfo carInfo = null;

        XmlPullParser parser = Xml.newPullParser();//创建pull解析器
        parser.setInput(is, "utf-8");

        int type = parser.getEventType();

        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("info".equals(parser.getName())) {//解析到全局开始的标签info根节点
                        list = new ArrayList<>();
                    } else if ("carId".equals(parser.getName())) {
                        carInfo = new CarInfo();
                    } else if ("make".equals(parser.getName())) {
                        carInfo.setMake(parser.nextText());         // parser.nextText() 得到该 tag 节点中的内容
                    } else if ("engine".equals(parser.getName())) {
                        carInfo.setEngine(parser.nextText());
                    } else if ("frame".equals(parser.getName())) {
                        carInfo.setFrame(parser.nextText());
                    } else if ("type".equals(parser.getName())) {
                        carInfo.setType(parser.nextText());
                    } else if ("licenseType".equals(parser.getName())) {
                        carInfo.setLicenseType(parser.nextText());
                    } else if ("licenseNum".equals(parser.getName())) {
                        carInfo.setLicenseNum(parser.nextText());
                    } else if ("VioNum".equals(parser.getName())) {
                        carInfo.setVioNum(parser.nextText());
                    } else if ("points".equals(parser.getName())) {
                        carInfo.setPoints(parser.nextText());
                    } else if ("fine".equals(parser.getName())) {
                        carInfo.setFine(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:

                    if ("carId".equals(parser.getName())) {
                        list.add(carInfo);
                        carInfo = null;
                        break;
                    }
            }
            type = parser.next();
        }
        return list;
    }
}
