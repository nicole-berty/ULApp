package myUL.timetable;


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

import myUL.R;

public class TimetableDisplay extends LinearLayout {
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

    static HashMap<Integer, TimetableIcons> event_icons = new HashMap<Integer, TimetableIcons>();
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
     * @param attrs
     */
    private void getAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Timetable_viewer);
        rowCount = a.getInt(R.styleable.Timetable_viewer_row_count, DEFAULT_ROW_COUNT) + 12;
        columnCount = a.getInt(R.styleable.Timetable_viewer_column_count, DEFAULT_COLUMN_COUNT) + 2;
        cellHeight = a.getDimensionPixelSize(R.styleable.Timetable_viewer_cell_height, dp2Px(DEFAULT_CELL_HEIGHT_DP));
        sideCellWidth = a.getDimensionPixelSize(R.styleable.Timetable_viewer_side_cell_width, dp2Px(DEFAULT_SIDE_CELL_WIDTH_DP + 3));
        int titlesId = a.getResourceId(R.styleable.Timetable_viewer_header_title, R.array.default_header_title);
        headerTitle = a.getResources().getStringArray(titlesId);
        int colorsId = a.getResourceId(R.styleable.Timetable_viewer_Icon_colors, R.array.default_Icon_color);
        iconColor = a.getResources().getStringArray(colorsId);
        startTime = a.getInt(R.styleable.Timetable_viewer_start_time, DEFAULT_START_TIME) - 9;
        headerHighlightColor = a.getColor(R.styleable.Timetable_viewer_header_highlight_color, getResources().getColor(R.color.default_header_highlight_color));
        int highlightTypeValue = a.getInteger(R.styleable.Timetable_viewer_header_highlight_type,0);
        if(highlightTypeValue == 0) highlightMode = TimetableHighlightIcon.COLOR;
        else if(highlightTypeValue == 1) highlightMode = TimetableHighlightIcon.IMAGE;
        headerHighlightImageSize = a.getDimensionPixelSize(R.styleable.Timetable_viewer_header_highlight_image_size, dp2Px(24));
        headerHighlightImage = a.getDrawable(R.styleable.Timetable_viewer_header_highlight_image);
        a.recycle();
    }

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

    public void add(ArrayList<TimetableEvent> schedules ) {
        add(schedules, -1);
    }

    private void add(final ArrayList<TimetableEvent> schedules, int specIdx) {
        final int count = specIdx < 0 ? ++iconCount : specIdx;
        TimetableIcons icon1 = new TimetableIcons();

        for (TimetableEvent schedule : schedules) {
            TextView tv = new TextView(context);

            RelativeLayout.LayoutParams param = createIconParam(schedule);
            tv.setLayoutParams(param);
            tv.setPadding(10, 0, 10, 0);
            String iconText = schedule.getEventName() + "\n" + schedule.getEventLocation() + "\n" + schedule.getSpeakerName();
            tv.setText(iconText);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_Icon_FONT_SIZE_DP);
            tv.setTypeface(null, Typeface.BOLD);

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (IconSelectedListener != null)
                        IconSelectedListener.OnIconSelected(count, schedules);
                }
            });

            icon1.addTextView(tv);
            icon1.addIcon(schedule);
            event_icons.put(count, icon1);
            iconBox.addView(tv);
        }
        setIconColor();
    }

    public void load(String data) {
        event_icons = TimetableSaveEvents.loadEvent(data);
        int maxKey = 0;
        for (int key : event_icons.keySet()) {
            ArrayList<TimetableEvent> schedules = Objects.requireNonNull(event_icons.get(key)).getCalendars();
            add(schedules, key);
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
        }
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

    public void remove(int idx) {

        //still crashes here sometimes - seems sporadic. If you create events, close the app, open it again and then try delete them
        //it seems to definitely crash once or twice but works perfectly other times?
        //Also, when it does work, the very next event created has -1 for all fields?? After that it's fine again
        TimetableIcons Icon = event_icons.get(idx);
//        assert Icon != null;
        for (TextView tv : Icon.getView()) {
            iconBox.removeView(tv);
        }
        event_icons.remove(idx);
        setIconColor();
        //if you put the below code first, it will execute and remove the event from the DB even with the crash
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
        }
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

    private RelativeLayout.LayoutParams createIconParam(TimetableEvent event) {
        int cell_w = calCellWidth();

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(cell_w, calIconHeightPx(event));
        param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        param.setMargins(sideCellWidth + cell_w * event.getDay(), calIconTopPxByTime(event.getStartTime()), 0, 0);

        return param;
    }

    private int calCellWidth(){
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int cell_w = (size.x-getPaddingLeft() - getPaddingRight()- sideCellWidth) / (columnCount - 1);
        return cell_w;
    }

    private int calIconHeightPx(TimetableEvent schedule) {
        int startTopPx = calIconTopPxByTime(schedule.getStartTime());
        int endTopPx = calIconTopPxByTime(schedule.getEndTime());

        return endTopPx - startTopPx;
    }

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

    private void onCreateByBuilder(Builder builder) {
        this.rowCount = builder.rowCount;
        this.columnCount = builder.columnCount;
        this.cellHeight = builder.cellHeight;
        this.sideCellWidth = builder.sideCellWidth;
        this.headerTitle = builder.headerTitle;
        this.iconColor = builder.IconColors;
        this.startTime = builder.startTime;
        this.headerHighlightColor = builder.headerHighlightColor;

        init();
    }


    public interface OnIconSelectedListener {
        void OnIconSelected(int idx, ArrayList<TimetableEvent> calendars);
    }

    static class Builder {
        private Context context;
        private int rowCount;
        private int columnCount;
        private int cellHeight;
        private int sideCellWidth;
        private String[] headerTitle;
        private String[] IconColors;
        private int startTime;
        private int headerHighlightColor;

        public Builder(Context context) {
            this.context = context;
            rowCount = DEFAULT_ROW_COUNT;
            columnCount = DEFAULT_COLUMN_COUNT;
            cellHeight = dp2Px(DEFAULT_CELL_HEIGHT_DP);
            sideCellWidth = dp2Px(DEFAULT_SIDE_CELL_WIDTH_DP);
            headerTitle = context.getResources().getStringArray(R.array.default_header_title);
            IconColors = context.getResources().getStringArray(R.array.default_Icon_color);
            startTime = DEFAULT_START_TIME;
            headerHighlightColor = context.getResources().getColor(R.color.default_header_highlight_color);
        }

        public Builder setRowCount(int n) {
            this.rowCount = n;
            return this;
        }

        public Builder setColumnCount(int n) {
            this.columnCount = n;
            return this;
        }

        public Builder setCellHeight(int dp) {
            this.cellHeight = dp2Px(dp);
            return this;
        }

        public Builder setSideCellWidth(int dp) {
            this.sideCellWidth = dp2Px(dp);
            return this;
        }

        public Builder setHeaderTitle(String[] titles) {
            this.headerTitle = titles;
            return this;
        }

        public Builder setIconColors(String[] colors) {
            this.IconColors = colors;
            return this;
        }

        public Builder setStartTime(int t) {
            this.startTime = t;
            return this;
        }

        public Builder setHeaderHighlightColor(int c) {
            this.headerHighlightColor = c;
            return this;
        }

        public TimetableDisplay build() {
            TimetableDisplay timetableViewer = new TimetableDisplay(context);
            timetableViewer.onCreateByBuilder(this);
            return timetableViewer;
        }
    }
}
