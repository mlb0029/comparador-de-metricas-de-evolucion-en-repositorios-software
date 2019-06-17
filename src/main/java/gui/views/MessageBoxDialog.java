package gui.views;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class MessageBoxDialog extends ConfirmDialog {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = 8931570651441420236L;
	
	private Icon captionIcon = new Icon();

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	public MessageBoxDialog() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param header
	 * @param text
	 * @param confirmText
	 * @param confirmListener
	 */
	public MessageBoxDialog(String header, String text, String confirmText,
			ComponentEventListener<ConfirmEvent> confirmListener) {
		super(header, text, confirmText, confirmListener);
	}

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param header
	 * @param text
	 * @param confirmText
	 * @param confirmListener
	 * @param cancelText
	 * @param cancelListener
	 */
	public MessageBoxDialog(String header, String text, String confirmText,
			ComponentEventListener<ConfirmEvent> confirmListener, String cancelText,
			ComponentEventListener<CancelEvent> cancelListener) {
		super(header, text, confirmText, confirmListener, cancelText, cancelListener);
	}

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param header
	 * @param text
	 * @param confirmText
	 * @param confirmListener
	 * @param rejectText
	 * @param rejectListener
	 * @param cancelText
	 * @param cancelListener
	 */
	public MessageBoxDialog(String header, String text, String confirmText,
			ComponentEventListener<ConfirmEvent> confirmListener, String rejectText,
			ComponentEventListener<RejectEvent> rejectListener, String cancelText,
			ComponentEventListener<CancelEvent> cancelListener) {
		super(header, text, confirmText, confirmListener, rejectText, rejectListener, cancelText, cancelListener);
	}

	public enum MessageBoxCaption {
		WARNING, INFO, QUESTION, ERROR
	}

	public MessageBoxDialog withCaption(MessageBoxCaption caption) {
		setCaption(caption);
		return this;
	}
	
	public MessageBoxDialog withCaption(Icon icon) {
		setCaption(icon);
		return this;
	}

	/**
	 * Gets the captionIcon.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the captionIcon
	 */
	public Icon getCaptionIcon() {
		return captionIcon;
	}

	/**
	 * Sets the captionIcon.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param captionIcon the captionIcon to set
	 */
	public void setCaption(Icon caption) {
		this.captionIcon = caption;
		captionIcon.setSize("4em");
		Span message = new Span(getElement().getProperty("message"));
		HorizontalLayout messageLayout = new HorizontalLayout(captionIcon, message);
		getElement().setProperty("message", "");
		setText(messageLayout);
	}

	/**
	 * Sets the captionIcon.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param captionIcon the captionIcon to set
	 */
	public void setCaption(MessageBoxCaption caption) {
		switch (caption) {
		case ERROR:
			captionIcon = new Icon(VaadinIcon.CLOSE_CIRCLE);
			captionIcon.setColor("red");
			break;
		case WARNING:
			captionIcon = new Icon(VaadinIcon.WARNING);
			captionIcon.setColor("orange");
			break;
		case INFO:
			captionIcon = new Icon(VaadinIcon.INFO_CIRCLE);
			captionIcon.setColor("blue");
			break;
		case QUESTION:
			captionIcon = new Icon(VaadinIcon.QUESTION_CIRCLE);
			captionIcon.setColor("#d1d616");
			break;
		default:
			captionIcon = new Icon();
			break;
		}
		captionIcon.setSize("4em");
		Span message = new Span(getElement().getProperty("message"));
		HorizontalLayout messageLayout = new HorizontalLayout(captionIcon, message);
		getElement().setProperty("message", "");
		setText(messageLayout);
	}
}
