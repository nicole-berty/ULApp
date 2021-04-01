package ie.ul.ulapp;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Timetable_viewer extends LinearLayout {
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

    static HashMap<Integer, Timetable_icons> event_icons = new HashMap<Integer, Timetable_icons>();
    private int iconCount = -1;

    private OnIconSelectedListener IconSelectedListener = null;

    private Timetable_highlight_icon highlightMode = Timetable_highlight_icon.COLOR;
    private int headerHighlightImageSize;
    private Drawable headerHighlightImage = null;

    public Timetable_viewer(Context context) {
        super(context, null);
        this.context = context;
    }

    public Timetable_viewer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Timetable_viewer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getAttrs(attrs);
        init();
    }

    /**
     * Gets all attributes to display the event
     * @param attrs
     */
    private void getAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Timetable_viewer);
        rowCount = a.getInt(R.styleable.Timetable_viewer_row_count, DEFAULT_ROW_COUNT) + 12;
        columnCount = a.getInt(R.styleable.Timetable_viewer_column_count, DEFAULT_COLUMN_COUNT) + 2;
        cellHeight = a.getDimensionPixelSize(R.styleable.Timetable_viewer_cell_height, dp2Px(DEFAULT_CELL_HEIGHT_DP));
        sideCellWidth = a.getDimensionPixelSize(R.styleable.Timetable_viewer_side_cell_width, dp2Px(DEFAULT_SIDE_CELL_WIDTH_DP));
        int titlesId = a.getResourceId(R.styleable.Timetable_viewer_header_title, R.array.default_header_title);
        headerTitle = a.getResources().getStringArray(titlesId);
        int colorsId = a.getResourceId(R.styleable.Timetable_viewer_Icon_colors, R.array.default_Icon_color);
        iconColor = a.getResources().getStringArray(colorsId);
        startTime = a.getInt(R.styleable.Timetable_viewer_start_time, DEFAULT_START_TIME) - 9;
        headerHighlightColor = a.getColor(R.styleable.Timetable_viewer_header_highlight_color, getResources().getColor(R.color.default_header_highlight_color));
        int highlightTypeValue = a.getInteger(R.styleable.Timetable_viewer_header_highlight_type,0);
        if(highlightTypeValue == 0) highlightMode = Timetable_highlight_icon.COLOR;
        else if(highlightTypeValue == 1) highlightMode = Timetable_highlight_icon.IMAGE;
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

    /**
     * get all schedules from TimetableView
     */
    public ArrayList<Timetable_Event> getAllSchedulesInIcons() {
        ArrayList<Timetable_Event> allSchedules = new ArrayList<Timetable_Event>();
        for (int key : event_icons.keySet()) {
            allSchedules.addAll(Objects.requireNonNull(event_icons.get(key)).getCalendars());
        }
        return allSchedules;
    }

    /**
     * Used in Edit mode, To check a invalidate schedule.
     */
    public ArrayList<Timetable_Event> getAllSchedulesInIconsExceptIdx(int idx) {
        ArrayList<Timetable_Event> allSchedules = new ArrayList<Timetable_Event>();
        for (int key : event_icons.keySet()) {
            if (idx == key) continue;
            allSchedules.addAll(Objects.requireNonNull(event_icons.get(key)).getCalendars());
        }
        return allSchedules;
    }

    public void add(ArrayList<Timetable_Event> schedules) {
        add(schedules, -1);
    }

    private void add(final ArrayList<Timetable_Event> schedules, int specIdx) {
        final int count = specIdx < 0 ? ++iconCount : specIdx;
        Timetable_icons icon1 = new Timetable_icons();
        for (Timetable_Event schedule : schedules) {
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
                    if(IconSelectedListener != null)
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
        removeAll();
        // call db here
        event_icons = Timetable_Save_Events.loadIcon(data);
        int maxKey = 0;
        for (int key : event_icons.keySet()) {
            ArrayList<Timetable_Event> schedules = Objects.requireNonNull(event_icons.get(key)).getCalendars();
            add(schedules, key);
            if (maxKey < key) maxKey = key;
        }
        iconCount = maxKey + 1;
        setIconColor();
    }

    /**
     *  Removes all events from calendar view
     */
    public void removeAll() {
        for (int key : event_icons.keySet()) {
            Timetable_icons Icon = event_icons.get(key);
            assert Icon != null;
            for (TextView tv : Icon.getView()) {
                iconBox.removeView(tv);
            }
        }
        event_icons.clear();
    }

    public void edit(int idx, ArrayList<Timetable_Event> schedules) {
        remove(idx);
        add(schedules, idx);
    }

    public void remove(int idx) {
        Timetable_icons Icon = event_icons.get(idx);
        assert Icon != null;
        for (TextView tv : Icon.getView()) {
            iconBox.removeView(tv);
        }
        event_icons.remove(idx);
        setIconColor();
    }

    public void setHeaderHighlight(int idx) {
        if(idx < 0)return;
        TableRow row = (TableRow) tableHeader.getChildAt(0);
        View element = row.getChildAt(idx);
        if(highlightMode == Timetable_highlight_icon.COLOR) {
            TextView tx = (TextView)element;
            tx.setTextColor(Color.parseColor("#FFFFFF"));
            tx.setBackgroundColor(headerHighlightColor);
            tx.setTypeface(null, Typeface.BOLD);
            tx.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEADER_HIGHLIGHT_FONT_SIZE_DP);
        }
        else if(highlightMode == Timetable_highlight_icon.IMAGE){
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

    private RelativeLayout.LayoutParams createIconParam(Timetable_Event schedule) {
        int cell_w = calCellWidth();

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(cell_w, calIconHeightPx(schedule));
        param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        param.setMargins(sideCellWidth + cell_w * schedule.getDay(), calIconTopPxByTime(schedule.getStartTime()), 0, 0);

        return param;
    }

    private int calCellWidth(){
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int cell_w = (size.x-getPaddingLeft() - getPaddingRight()- sideCellWidth) / (columnCount - 1);
        return cell_w;
    }

    private int calIconHeightPx(Timetable_Event schedule) {
        int startTopPx = calIconTopPxByTime(schedule.getStartTime());
        int endTopPx = calIconTopPxByTime(schedule.getEndTime());
        int d = endTopPx - startTopPx;

        return d;
    }

    private int calIconTopPxByTime(Timetable_Time_Keeper time) {
        int topPx = (time.getHour() - startTime) * cellHeight + (int) ((time.getMinute() / 60.0f) * cellHeight);
        return topPx;
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
        int p = (startTime + i) % 24;
        int res = p <= 12 ? p : p - 12;
        return res + "";
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
        void OnIconSelected(int idx, ArrayList<Timetable_Event> calendars);
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

        public Timetable_viewer build() {
            Timetable_viewer timetableViewer = new Timetable_viewer(context);
            timetableViewer.onCreateByBuilder(this);
            return timetableViewer;
        }
    }
}
