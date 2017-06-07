package com.hikarisakamoto.myportfoliobasic.TopDownloader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class ParseEntries {
    private static final String TAG = "ParseEntries";

    private ArrayList<FeedEntry> entries;

    public ParseEntries() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getEntries() {
        return entries;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        FeedEntry currentRecord = null;
        boolean inEntry = false;
        boolean gotImage = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader((xmlData)));

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();

                switch (eventType) {

                    case XmlPullParser.START_TAG:
//                        Log.d(TAG, "parse: Strarting tag for " + tagName);
                        if ("entry".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentRecord = new FeedEntry();
                        } else if (("image".equalsIgnoreCase(tagName)) && inEntry) {
                            String imageResolution = xmlPullParser.getAttributeValue(null, "height");
                            if (imageResolution != null) {
                                if ("100".equalsIgnoreCase(imageResolution) || "170".equalsIgnoreCase(imageResolution)) {
                                    gotImage = true;
                                }
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xmlPullParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
//                        Log.d(TAG, "parse: Ending tag for " + tagName);
                        if (inEntry) {
                            if ("entry".equalsIgnoreCase(tagName)) {
                                entries.add(currentRecord);
                                inEntry = false;
                            } else if ("name".equalsIgnoreCase(tagName)) {
                                currentRecord.setName(textValue);

                            } else if ("artist".equalsIgnoreCase(tagName)) {
                                currentRecord.setArtist(textValue);

                            } else if ("releaseDate".equalsIgnoreCase(tagName)) {
                                currentRecord.setReleaseDate(textValue);

                            } else if ("summary".equalsIgnoreCase(tagName)) {
                                currentRecord.setSummary(textValue);

                            } else if ("image".equalsIgnoreCase(tagName)) {
                                if (gotImage) {
                                    currentRecord.setImageURL(textValue);
                                }
                            }
                        }
                        break;

                    default:
                        // Nothing else to do...
                }
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}
