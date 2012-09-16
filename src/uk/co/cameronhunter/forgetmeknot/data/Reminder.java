package uk.co.cameronhunter.forgetmeknot.data;

public final class Reminder {

    public final Long id;
    public final CharSequence text;

    public Reminder( CharSequence text ) {
        this( null, text );
    }

    public Reminder( long id, CharSequence text ) {
        this( Long.valueOf( id ), text );
    }

    private Reminder( Long id, CharSequence text ) {
        this.id = id;
        this.text = text;
    }
    
    @Override
    public String toString() {
        return String.format( "Reminder[id:%s, text:%s]", id, text );
    }

}
