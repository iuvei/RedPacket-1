package me.jessyan.armscomponent.commonres.view.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;
    int mOrientation;
    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     * <p>
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * <p>
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        switch (mOrientation){
            case ALL:
                if(mOrientation == ALL){
                    outRect.left = mSpace;
                    outRect.right = mSpace;
                    outRect.bottom = mSpace;
                    if (parent.getChildAdapterPosition(view) == 0) {
                        outRect.top = mSpace;
                    }
                }
                break;

            case HORIZONTAL:
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = mSpace;
                }
                outRect.right =mSpace;
                break;

            case VERTICAL:
//                if (parent.getChildAdapterPosition(view) == 0) {
//                    outRect.top = mSpace;
//                }
                outRect.bottom = mSpace;
                break;

            default:
                break;
        }


    }

    public SpaceItemDecoration(int space, int orientation) {
        this.mOrientation = orientation;
        this.mSpace = space;
    }

    public static final int ALL = 0;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;


}