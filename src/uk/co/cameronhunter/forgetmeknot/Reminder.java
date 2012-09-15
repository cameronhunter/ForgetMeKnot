package uk.co.cameronhunter.forgetmeknot;

public final class Reminder {

    public final Long id;
    public final String text;

    public Reminder( String text ) {
        this( null, text );
    }

    public Reminder( long id, String text ) {
        this( Long.valueOf( id ), text );
    }

    private Reminder( Long id, String text ) {
        this.id = id;
        this.text = text;
    }

    public boolean isSaved() {
        return id != null;
    }
    
    @Override
    public String toString() {
        return text;
    }
}
