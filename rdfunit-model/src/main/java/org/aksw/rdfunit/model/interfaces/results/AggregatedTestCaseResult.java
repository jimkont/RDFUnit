package org.aksw.rdfunit.model.interfaces.results;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import org.aksw.rdfunit.enums.TestCaseResultStatus;
import org.aksw.rdfunit.model.interfaces.TestCase;
import org.aksw.rdfunit.vocabulary.RDFUNITv;

/**
 * The type Aggregated test case result.
 *
 * @author Dimitris Kontokostas
 *         Description
 * @since 1 /2/14 3:44 PM
 * @version $Id: $Id
 */
public class AggregatedTestCaseResult extends StatusTestCaseResult {
    private final long errorCount;
    private final long prevalenceCount;

    /**
     * Instantiates a new Aggregated test case result.
     *
     * @param testCase        the test case
     * @param errorCount      the error count
     * @param prevalenceCount the prevalence count
     */
    public AggregatedTestCaseResult(TestCase testCase, long errorCount, long prevalenceCount) {
        super(testCase, TestCaseResultStatus.resolve(errorCount));
        this.errorCount = errorCount;
        this.prevalenceCount = prevalenceCount;
    }

    /**
     * Instantiates a new Aggregated test case result.
     *
     * @param testCase        the test case
     * @param status          the status
     * @param errorCount      the error count
     * @param prevalenceCount the prevalence count
     */
    public AggregatedTestCaseResult(TestCase testCase, TestCaseResultStatus status, long errorCount, long prevalenceCount) {
        super(testCase, status);
        this.errorCount = errorCount;
        this.prevalenceCount = prevalenceCount;
    }

    /** {@inheritDoc} */
    @Override
    public Resource serialize(Model model, String testExecutionURI) {
        return super.serialize(model, testExecutionURI)
                .addProperty(RDF.type, RDFUNITv.AggregatedTestResult)
                .addProperty(RDFUNITv.resultCount,
                        ResourceFactory.createTypedLiteral("" + errorCount, XSDDatatype.XSDinteger))
                .addProperty(RDFUNITv.resultPrevalence,
                        ResourceFactory.createTypedLiteral("" + prevalenceCount, XSDDatatype.XSDinteger));
    }

    /**
     * Gets error count.
     *
     * @return the error count
     */
    public long getErrorCount() {
        return errorCount;
    }

    /**
     * Gets prevalence count.
     *
     * @return the prevalence count
     */
    public long getPrevalenceCount() {
        return prevalenceCount;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {

        String localTestUri = getTestCaseUri();
        if (getTestCase().isPresent()) {
            localTestUri = getTestCase().get().getAbrTestURI();
        }

        return "Errors: " + errorCount + " / Prevalence: " + prevalenceCount + ". Test: " + localTestUri;
    }
}