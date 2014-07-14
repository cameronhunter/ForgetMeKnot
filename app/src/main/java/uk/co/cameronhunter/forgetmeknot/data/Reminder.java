package uk.co.cameronhunter.forgetmeknot.data;

public final class Reminder {

    public final Long id;
    public final CharSequence text;
    public final boolean deleted;

    public Reminder(CharSequence text) {
        this(null, text, false);
    }

    public Reminder(long id, CharSequence text) {
        this(Long.valueOf(id), text, false);
    }

    public Reminder(long id, CharSequence text, boolean deleted) {
        this(Long.valueOf(id), text, deleted);
    }

    private Reminder(Long id, CharSequence text, boolean deleted) {
        this.id = id;
        this.text = text;
        this.deleted = deleted;
    }

    public boolean isSaved() {
        return id != null && id > 0;
    }

    @Override
    public String toString() {
        return String.format("Reminder[id:%s, text:%s]", id, text);
    }
}
