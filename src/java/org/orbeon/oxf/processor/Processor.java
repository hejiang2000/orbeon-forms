/**
 *  Copyright (C) 2004 Orbeon, Inc.
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU Lesser General Public License as published by the Free Software Foundation; either version
 *  2.1 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  The full text of the license is available at http://www.gnu.org/copyleft/lesser.html
 */
package org.orbeon.oxf.processor;

import org.dom4j.QName;
import org.orbeon.oxf.xml.dom4j.LocationData;
import org.orbeon.oxf.pipeline.api.PipelineContext;

import java.util.List;
import java.util.Map;

/**
 * Base interface implemented by all processors.
 *
 * @see org.orbeon.oxf.processor.ProcessorImpl
 */
public interface Processor {

    /**
     * A processor may have an "identifier". The identifier has no implication on the behaviour of
     * the processor. It is there for information only (for instance, it will be displayed by the
     * Inspector). In the pipeline language, the id is specified with the "id" attribute of the
     * <p:processor> element.
     *
     * @return the identifier of this processor
     */
    public String getId();

    /**
     * @param id  the new id of this processor
     * @see #getId()
     */
    public void setId(String id);

    /**
     * When this processor is created based on a declaration in an XML document, the LocationData
     * provides information about the location of this declaration. Typically, if this processor
     * corresponds to a <p:processor> a PDL file, the LocationData holds information regarding the
     * position of the <p:processor> element in the PDL file.
     *
     * @return the LocationData for this processor
     */
    public LocationData getLocationData();

    /**
     * @param locationData  the new LocationData of this processor
     * @see #getLocationData()
     */
    public void setLocationData(LocationData locationData);

    /**
     * Name of the processor, if it has been created by a factory and that factory has a name. The
     * name has no implication on the behaviour of the processor. It is there for information only.
     *
     * @return The name of this processor
     */
    public QName getName();

    /**
     * @param  name  The new name of this processor
     * @see #getName()
     */
    public void setName(QName name);

    /**
     * Creates a new input on this processor. The new input can then be connected to the output of
     * an other processor. This method cannot be called twice with the same name on the same
     * processor.
     *
     * @param   name  Name of the input to create
     * @return  The newly created input
     */
    public ProcessorInput createInput(String name);

    /**
     * Deletes an input previously created with <code>createInput(String
     * name)</code>
     *
     * @param  name  Name of the input to delete
     * @see    #createInput(java.lang.String)
     */
    public void deleteInput(ProcessorInput name);

    /**
     * @param   name  Name of the input
     * @return  The inputs that have been previously created with
     *          <code>createInput(String name)</code>. Returns <code>null</code>
     *          if there is no existing input with this name.
     * @see     #createInput(java.lang.String)
     */
    public ProcessorInput getInputByName(String name);

    /**
     * Creates a new output on this processor. The output can then be connected
     * to the input of an other processor. This method cannnot be called twice
     * on the same processor with the same name.
     *
     * @param   name  Name of the output to create
     * @return  The newly created output
     */
    public ProcessorOutput createOutput(String name);

    /**
     * Deletes an output previously created with <code>createOutput(String
     * name)</code>
     *
     * @param  output  Name of the output to delete
     * @see    #createOutput(java.lang.String)
     */
    public void deleteOutput(ProcessorOutput output);

    /**
     * @param   name  Name of the output
     * @return  The outputs that have been previously created with
     *          <code>createOutput(String name)</code>. Returns
     *          <code>null</code> if there is no existing output with this name.
     * @see     #createOutput(java.lang.String)
     */
    public ProcessorOutput getOutputByName(String name);

    /**
     * @return  A list of <code>ProcessorInputOutputInfo</code> objects
     *          corresponding to the inputs that can be created on this
     *          processor. This exposes the "input API" of this processor.
     */
    public List getInputsInfo();

    /**
     * @return  A list of <code>ProcessorInputOutputInfo</code> objects
     *          corresponding to the outputs that can be created on this
     *          processor. This exposes the "outputs API" of this processor.
     */
    public List getOutputsInfo();

    /**
     * @return  A read-only Map containing all the inputs currently connected
     *          to this processor. Each key in the Map is a String specifying
     *          an input name. The List associated to the key contains one or
     *          more <code>ProcessorInput</code> objects.
     */
    public Map getConnectedInputs();

    /**
     * @return  A read-only Map containing all the outputs currently connected
     *          to this processor. Each key in the Map is a String specifying
     *          an output name. The List associated to the key contains one or
     *          more <code>ProcessorOutput</code> objects.
     */
    public Map getConnectedOutputs();

    /**
     * This method is called to trigger the execution of this processor. This
     * method can only be called on processor with no outputs (so-called
     * serializers). If this processor has outputs, the method <code>read</code>
     * should be called on the outputs instead.
     *
     * @param  context  Context in which the processor is executed
     */
    public void start(PipelineContext context);

    /**
     * Resets the processor. This method is called before the processor is
     * executed (either by calling <code>read</read> its outputs, or by calling
     * <code>start</code> on the processor.
     *
     * @param context Context in which the processor is executed
     */
    public void reset(PipelineContext context);
}
