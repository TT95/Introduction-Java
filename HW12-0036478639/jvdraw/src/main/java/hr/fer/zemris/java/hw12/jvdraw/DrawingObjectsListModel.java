package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import javax.swing.AbstractListModel;

/**
 * Model for list created for {@link JVDraw}.
 * It acts like adapter on {@link IDrawingModel}.
 * COntains private property on model adapting and also must me added
 * as listener to that adapting model.
 * When adapting model fires this class must follow.
 * 
 * @author Teo Toplak
 *
 */
public class DrawingObjectsListModel extends
		AbstractListModel<GeometricalObject> implements IDrawingModel,  IDrawingModelListener{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * model on which this model acts like adapter
	 */
	private IDrawingModel model;
	
	/**
	 * Constructor taking single argument, model for adapting
	 * @param model model to be adapted
	 */
	public DrawingObjectsListModel(IDrawingModel model) {
		super();
		this.model = model;
	}

	@Override
	public GeometricalObject getElementAt(int arg0) {
		return model.getObject(arg0);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return model.getObject(index);
	}

	@Override
	public void add(GeometricalObject o) {
		 model.add(o);
	}

	@Override
	public void addDrawingModelListener(IDrawingModelListener l) {
		model.addDrawingModelListener(l);
	}

	@Override
	public void removeDrawingModelListener(IDrawingModelListener l) {
		model.removeDrawingModelListener(l);
	}

	@Override
	public void objectsAdded(IDrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsChanged(IDrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

	@Override
	public void objectsRemoved(IDrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

}
