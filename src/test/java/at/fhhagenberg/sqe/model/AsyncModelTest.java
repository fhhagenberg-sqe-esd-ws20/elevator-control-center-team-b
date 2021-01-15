package at.fhhagenberg.sqe.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AsyncModelTest {

	
	@Test
	void DoesFirePropertyChange() {
		var cut = new AsyncModel() {

			@Override
			public void run() {
				setProperty("test", 1);
				
			}
			
		};
		
		var listener = Mockito.mock(PropertyChangeListener.class, Mockito.CALLS_REAL_METHODS);
		cut.addListener(listener);
		
		cut.run();
		
		Mockito.verify(listener).propertyChange(Mockito.argThat((PropertyChangeEvent evt) -> {
			
			return evt.getSource() == cut && evt.getPropertyName().equals("test") && (int)evt.getNewValue() == 1;
		}));
	}
	
}
