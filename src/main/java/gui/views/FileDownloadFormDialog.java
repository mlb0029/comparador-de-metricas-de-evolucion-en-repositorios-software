package gui.views;

import java.io.IOException;
import java.io.InputStream;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.StreamResource;

/**
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class FileDownloadFormDialog extends MessageBoxDialog {

	/**
	 * Description.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private static final long serialVersionUID = -3024115021992696619L;
	private static final String HEADER = "File download";
	private static final String MESSAGE = "Export repositories?";
	private static final String DEFAULT_FILE_NAME = "evolution-metrics_reposiories.txt";

	/**
	 * Constructor.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @throws IOException 
	 */
	public FileDownloadFormDialog(InputStream inputStream) {
		DownloadLink downloadLink= new DownloadLink(DEFAULT_FILE_NAME, inputStream);

		setHeader(HEADER);
		setText(MESSAGE);
		setConfirmButton(downloadLink);
		setCancelable(true);
	}

	private static class DownloadLink extends Anchor {

		private static final long serialVersionUID = 8876419840558440068L;

		private static final String BUTTON_TEXT = "Download";

		private Button downloadButton = new Button(BUTTON_TEXT, VaadinIcon.DOWNLOAD.create());

//		private InputStream content;

		public DownloadLink(String filename, InputStream in) {
//			this.content = in;
			setHref(getStreamResource(filename, in));
			getElement().setAttribute("download", true);
			add(downloadButton);
		}

//		public void setFilename(String filename) {
//			setHref(getStreamResource(filename, content));
//		}

		private StreamResource getStreamResource(String filename, InputStream in) {
			return new StreamResource(filename, () -> {
				return in;
			});
		}

		/* (non-Javadoc)
		 * @see com.vaadin.flow.component.HasEnabled#setEnabled(boolean)
		 */
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			downloadButton.setEnabled(enabled);
		}
	}
}
