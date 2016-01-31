package za.co.moneymanager.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collection;

import za.co.moneymanager.model.SmsDao;

/**
 * Created by michaeljacobs on 9/4/2015.
 */
public class SmsMessagingService {
    private static final String SMS_INBOX_URI = "content://sms/inbox";
    private static final String SMS_SENT_URI = "content://sms/sent";
    private static final String SMS_DRAFT_URI = "content://sms/draft";
    //{ "_id", "thread_id", "address", "person", "date", "protocol", "read", "status", "type", "reply_path_present", "subject", "body", "service_center", "locked" }

    public static Collection<SmsDao> readSmsInbox(Context context) {

        Uri inboxUri = Uri.parse(SMS_INBOX_URI);
        String[] reqCols = new String[] { "_id", "address", "person", "date", "body" };

        ContentResolver contentResolver = context.getContentResolver();
        //contentResolver.query(inboxUri, reqCols, null, null, null);
        Cursor cursor = contentResolver.query(inboxUri, reqCols, "lower(body) LIKE ?", new String[]{"%absa%"}, null);
        Collection<SmsDao> absaSmsDaoCollection = new ArrayList<SmsDao>();
        absaSmsDaoCollection = getSmsDaoFromCursor( cursor);
        //TODO: Can check here for other banks
        //
        return absaSmsDaoCollection;
    }

    private static Collection<SmsDao> getSmsDaoFromCursor(Cursor cursor) {
        Collection<SmsDao> smsDaoCollection = new ArrayList<SmsDao>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(cursor.isAfterLast() == false) {
                String idNumber = cursor.getString(0);
                String address = cursor.getString(1);
                String person = cursor.getString(2);
                String date = cursor.getString(3);
                String body = cursor.getString(4);

                SmsDao smsDao = new SmsDao(idNumber, address, person, date, body);
                smsDaoCollection.add(smsDao);
                cursor.moveToNext();
            }

        }

        return smsDaoCollection;
    }
}
