This is Java FX application called Chat Viewer which can read file with specific format and structure. The requirements are next:
Conversation specification
Conversation between one and more users consists of separate messages which can be considered
as a set of various elements:
TIME_STAMP - a time and a date of message creation.
NICK_NAME - a nick of a user who created a message.
CONTENT - textual content of the message. Two different emoticons can occur in the text and
they can be considered as standalone elements.
SMILE_HAPPY - an emoticon representing a happy smile face occurring in the text as ":)".
SMILE_SAD - an emoticon representing a sad smiley face occurring in the text as ":(".
Graphical interface specification
The application will consist of one window with at least three JavaFX elements - TextFlow, Button and
Label. The button opens dialogue (realized by a FileChooser component) and allows a user to
choose and open a file with the conversation. Since the file is opened correctly, its path and name are
displayed in the Label component. The conversation is displayed in TextFlow component. Every
message occupies exactly one line. The following list describes a graphical representation of every
element:
TIME_STAMP - the text is surrounded by the square parenthesis - "[" and "]"
NICK_NAME - the text has blue colour and it is followed by the colon - ":". If the previous displayed
message has the same nickname, the nickname in the following message is replaced by "...".
CONTENT - the text is black and bold.
SMILE_HAPPY and SMILE_SAD - all occurrences are replaced by pictures, which can be found in
an appendix on Moodle. Every message displayed in JTextPane has the following structure:
[TIME_STAMP]NICK_NAME:CONTENT In the appendix on Moodle is published a picture of the
suggested appearance.
Conversation file format specification
The file with a conversation has a "msg" suffix. Every message is represented by three lines of text:
The first line specifies the TIME_STAMP element. This line starts with the text "Time:" and all
remaining text of the line represents the time stamp. The format of the timestamp is not specified.
It could be any text therefore syntax checking is not needed. Any non-empty string should be
accepted.
The second line specifies the NICK_NAME element. The line starts with the text "Name:" and all
remaining text the line is considered as the nickname. Any non-empty string should be accepted.
The third line specifies the CONTENT element. The line starts with the text "Message:" and all
remaining text of the line is considered as the message itself. An empty string is considered a valid
message too.
All messages are separated by an empty line. The file has to end directly after the last message,
which means that there is no empty line at the end of the file.
