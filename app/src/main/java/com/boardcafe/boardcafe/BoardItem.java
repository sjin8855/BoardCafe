package com.boardcafe.boardcafe;

public class BoardItem {
    private String _id;
    private String _contents;

    public BoardItem() {}

    public BoardItem(String id, String contents){
        this._id = id;
        this._contents = contents;
    }

    public String getId(){
        return _id;
    }

    public void setId(String id){
        this._id = id;
    }

    public String getContents() {
        return _contents;
    }

    public void setContents(String contents) {
        this._contents = contents;
    }

    @Override
    public String toString(){
        return "BoardItem{ID='" + _id + "\'" + ", Contents='" + _contents + "\'}";
    }
}
