package ulapp.myUL.timetable;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ulapp.myUL.R;

public class TimetableDisplay extends LinearLayout {

    //Create variables for the class
    private static final int DEFAULT_ROW_COUNT = 12;
    private static final int DEFAULT_COLUMN_COUNT = 6;
    private static final int DEFAULT_CELL_HEIGHT_DP = 50;
    private static final int DEFAULT_SIDE_CELL_WIDTH_DP = 30;
    private static final int DEFAULT_START_TIME = 9;

    private static final int DEFAULT_SIDE_HEADER_FONT_SIZE_DP = 13;
    private static final int DEFAULT_HEADER_FONT_SIZE_DP = 15;
    private static final int DEFAULT_HEADER_HIGHLIGHT_FONT_SIZE_DP = 15;
    private static final int DEFAULT_Icon_FONT_SIZE_DP = 13;


    private int rowCount;
    private int columnCount;
    private int cellHeight;
    private int sideCellWidth;
    private String[] headerTitle;
    private String[] iconColor;
    private int startTime;
    private int headerHighlightColor;

    private RelativeLayout iconBox;
    TableLayout tableHeader;
    TableLayout tableBox;

    private Context context;

    static HashMap<Integer, TimetableIcons> event_icons = new HashMap<>();
    private int iconCount = 1;

    private OnIconSelectedListener IconSelectedListener = null;

    private TimetableHighlightIcon highlightMode = TimetableHighlightIcon.COLOR;
    private int headerHighlightImageSize;
    private Drawable headerHighlightImage = null;

    public TimetableDisplay(Context context) {
        super(context, null);
        this.context = context;
    }

    public TimetableDisplay(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimetableDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getAttrs(attrs);
        init();
    }

    /**
     * Gets all attributes to display events.
     * @param attrs Attributes for the timetable
     */
    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Timetable_viewer);
        rowCount = typedArray.getInt(R.styleable.Timetable_viewer_row_count, DEFAULT_ROW_COUNT) + 12;
        columnCount = typedArray.getInt(R.styleable.Timetable_viewer_column_count, DEFAULT_COLUMN_COUNT) + 2;
        cellHeight = typedArray.getDimensionPixelSize(R.styleable.Timetable_viewer_cell_height, dp2Px(DEFAULT_CELL_HEIGHT_DP));
        sideCellWidth = typedArray.getDimensionPixelSize(R.styleable.Timetable_viewer_side_cell_width, dp2Px(DEFAULT_SIDE_CELL_WIDTH_DP + 3));
        int titlesId = typedArray.getResourceId(R.styleable.Timetable_viewer_header_title, R.array.default_header_title);
        headerTitle = typedArray.getResources().getStringArray(titlesId);
        int colorsId = typedArray.getResourceId(R.styleable.Timetable_viewer_Icon_colors, R.array.default_Icon_color);
        iconColor = typedArray.getResources().getStringArray(colorsId);
        startTime = typedArray.getInt(R.styleable.Timetable_viewer_start_time, DEFAULT_START_TIME) - 9;
        headerHighlightColor = typedArray.getColor(R.styleable.Timetable_viewer_header_highlight_color, getResources().getColor(R.color.default_header_highlight_color));
        int highlightTypeValue = typedArray.getInteger(R.styleable.Timetable_viewer_header_highlight_type,0);
        if(highlightTypeValue == 0) highlightMode = TimetableHighlightIcon.COLOR;
        else if(highlightTypeValue == 1) highlightMode = TimetableHighlightIcon.IMAGE;
        headerHighlightImageSize = typedArray.getDimensionPixelSize(R.styleable.Timetable_viewer_header_highlight_image_size, dp2Px(24));
        headerHighlightImage = typedArray.getDrawable(R.styleable.Timetable_viewer_header_highlight_image);
        typedArray.recycle();
    }

    /**
     * Initialize to display timetable view
     */
    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.timetable, this, false);
        addView(view);

        iconBox = view.findViewById(R.id.icon_box);
        tableHeader = view.findViewById(R.id.table_header);
        tableBox = view.findViewById(R.id.table_box);

        createTable();
    }

    public void setOnIconSelectEventListener(OnIconSelectedListener listener) {
        IconSelectedListener = listener;
    }


    public void add(ArrayList<TimetableEvent> events ) {
        add(events, -1);
    }

    /**
     * Add new event to timetable view
     * @param events
     * @param specIdx
     */
    private void add(final ArrayList<TimetableEvent> events, int specIdx) {
        final int count = specIdx < 0 ? ++iconCount : specIdx;
        TimetableIcons icon1 = new TimetableIcons();

        for (TimetableEvent event : events) {
            TextView tv = new TextView(context);

            RelativeLayout.LayoutParams param = createIconParam(event);
            tv.setLayoutParams(param);
            tv.setPadding(10, 0, 10, 0);
            String iconText = event.getEventName() + "\n" + event.getEventLocation() + "\n" + event.getSpeakerName();
            tv.setText(iconText);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_Icon_FONT_SIZE_DP);
            tv.setTypeface(null, Typeface.BOLD);

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (IconSelectedListener != null)
                        IconSelectedListener.OnIconSelected(count, events);
                }
            });

            icon1.addTextView(tv);
            icon1.addIcon(event);
            event_icons.put(count, icon1);
            iconBox.addView(tv);
        }
        setIconColor();
    }

    /**
     * Load events onto the timetable view
     * @param data
     */
    public void load(String data) {
        event_icons = TimetableSaveEvents.loadEvent(data);
        int maxKey = 0;
        for (int key : event_icons.keySet()) {
            ArrayList<TimetableEvent> events = Objects.requireNonNull(event_icons.get(key)).getCalendars();
            add(events, key);
            if (maxKey < key) maxKey = key;
        }
        iconCount = maxKey;// + 1;
        setIconColor();
    }

    /**
     *  Removes all events from calendar view
     */
    public void removeAll() {

        for (int key : event_icons.keySet()) {
            TimetableIcons Icon = event_icons.get(key);
            assert Icon != null;
            for (TextView tv : Icon.getView()) {
                iconBox.removeView(tv);
            }
        }
        event_icons.clear();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
            db.collection("timetable").document(email)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            TimetableActivity.loadFromDatabase();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error deleting document", e);
                        }
                    });
        }
    }

    /**
     * Removes a specific event from database and timetable view
     * @param idx
     */
    public void remove(int idx) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
            DocumentReference docRef = db.collection("timetable").document(email);
            String index = "{\"idx\":"+ idx + "}";
            Map<String,Object> delete_event = new HashMap<>();
            delete_event.put(index, FieldValue.delete());

            docRef.update(delete_event).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    System.out.println("Successfully Deleted");
                }
            });
        }
    }

    /**
     * Highlights the day of week in the header on timetable
     * @param idx
     */
    public void setHeaderHighlight(int idx) {
        if(idx < 0)return;
        TableRow row = (TableRow) tableHeader.getChildAt(0);
        View element = row.getChildAt(idx);
        if(highlightMode == TimetableHighlightIcon.COLOR) {
            TextView tx = (TextView)element;
            tx.setTextColor(Color.parseColor("#FFFFFF"));
            tx.setBackgroundColor(headerHighlightColor);
            tx.setTypeface(null, Typeface.BOLD);
            tx.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEADER_HIGHLIGHT_FONT_SIZE_DP);
        }
        else if(highlightMode == TimetableHighlightIcon.IMAGE){
            RelativeLayout outer = new RelativeLayout(context);
            outer.setLayoutParams(createTableRowParam(cellHeight));
            ImageView iv = new ImageView(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(headerHighlightImageSize,headerHighlightImageSize);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            row.removeViewAt(idx);
            outer.addView(iv);
            row.addView(outer,idx);

            if(headerHighlightImage != null) {
                iv.setImageDrawable(headerHighlightImage);
            }
        }
    }

    /**
     * Sets the icon color
     */
    private void setIconColor() {
        int size = event_icons.size();
        int[] orders = new int[size];
        int i = 0;
        for (int key : event_icons.keySet()) {
            orders[i++] = key;
        }
        Arrays.sort(orders);
        int colorSize = iconColor.length;
        for (i = 0; i < size; i++) {
            for (TextView v : event_icons.get(orders[i]).getView()) {
                v.setBackgroundColor(Color.parseColor(iconColor[i % (colorSize)]));
            }
        }

    }

    /**
     * Creates timetable view
     */
    private void createTable() {
        createTableHeader();
        for (int i = 0; i < rowCount; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(createTableLayoutParam());

            for (int k = 0; k < columnCount; k++) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(createTableRowParam(cellHeight));
                if (k == 0) {
                    tv.setText(getHeaderTime(i));
                    tv.setTextColor(getResources().getColor(R.color.colorHeaderText));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_SIDE_HEADER_FONT_SIZE_DP);
                    tv.setBackgroundColor(getResources().getColor(R.color.colorHeader));
                    tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                    tv.setLayoutParams(createTableRowParam(sideCellWidth, cellHeight));
                } else {
                    tv.setText("");
                    tv.setBackground(getResources().getDrawable(R.drawable.item_border_timetable));
                    tv.setGravity(Gravity.RIGHT);
                }
                tableRow.addView(tv);
            }
            tableBox.addView(tableRow);
        }
    }

    /**
     * Creates header for timetable
     */
    private void createTableHeader() {
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(createTableLayoutParam());

        for (int i = 0; i < columnCount; i++) {
            TextView tv = new TextView(context);
            if (i == 0) {
                tv.setLayoutParams(createTableRowParam(sideCellWidth, cellHeight));
            } else {
                tv.setLayoutParams(createTableRowParam(cellHeight));
            }
            tv.setTextColor(getResources().getColor(R.color.colorHeaderText));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEADER_FONT_SIZE_DP);
            tv.setText(headerTitle[i]);
            tv.setGravity(Gravity.CENTER);

            tableRow.addView(tv);
        }
        tableHeader.addView(tableRow);
    }

    /**
     * Sets parameters for each icon event
     * @param event
     * @return
     */
    private RelativeLayout.LayoutParams createIconParam(TimetableEvent event) {
        int cell_w = calCellWidth();
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(cell_w, calIconHeightPx(event));
        param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        param.setMargins(sideCellWidth + cell_w * event.getDay(), calIconTopPxByTime(event.getStartTime()), 0, 0);
        return param;
    }

    /**
     * Calculates the width of the event on the timetable
     * @return Event Cell Width
     */
    private int calCellWidth(){
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int cell_width = (size.x-getPaddingLeft() - getPaddingRight()- sideCellWidth) / (columnCount - 1);
        return cell_width;
    }

    /**
     * Calculates the height of the event on the timetable
     * @param event
     * @return Event Cell Height
     */
    private int calIconHeightPx(TimetableEvent event) {
        int startPX = calIconTopPxByTime(event.getStartTime());
        int endPX = calIconTopPxByTime(event.getEndTime());

        return endPX - startPX;
    }

    /**
     * Calculates the start and end pixel for events
     * @param time
     * @return Event Cell Start and End pixel
     */
    private int calIconTopPxByTime(TimetableTimeKeeper time) {
        return (time.getHour() - startTime) * cellHeight + (int) ((time.getMinute() / 60.0f) * cellHeight);
    }

    private TableLayout.LayoutParams createTableLayoutParam() {
        return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
    }

    private TableRow.LayoutParams createTableRowParam(int h_px) {
        return new TableRow.LayoutParams(calCellWidth(), h_px);
    }

    private TableRow.LayoutParams createTableRowParam(int w_px, int h_px) {
        return new TableRow.LayoutParams(w_px, h_px);
    }

    private String getHeaderTime(int i) {
        int hour_of_day = (startTime + i) % 24;
        return hour_of_day + "";
    }

    static private int dp2Px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public interface OnIconSelectedListener {
        void OnIconSelected(int idx, ArrayList<TimetableEvent> calendars);
    }

}
