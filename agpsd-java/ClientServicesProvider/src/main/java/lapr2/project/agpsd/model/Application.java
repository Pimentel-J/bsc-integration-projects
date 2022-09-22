package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lapr2.project.agpsd.utils.ForEachLists;
import lapr2.project.agpsd.utils.OutOfBoundArgumentException;
import lapr2.project.agpsd.utils.Utils;

/**
 * This class enables the unregistered user to submit a service provider
 * application.
 *
 *
 */
public class Application {

    /**
     * Unregistered user's full name
     */
    private String fullName;
    /**
     * Unregistered user's NIF
     */
    private String NIF;
    /**
     * Unregistered user's email
     */
    private String email;
    /**
     * Unregistered user's phone number
     */
    private String phoneNumber;
    /**
     * Unregistered user's postal address
     */
    private PostalAddress postalAddress;
    /**
     * Initializes categories list
     */
    private List<Category> categories;
    /**
     * Initializes academic qualifications list
     */
    private List<AcademicQualification> academicQualifications;
    /**
     * Initializes professional qualifications list
     */
    private List<ProfessionalQualification> professionalQualifications;
    /**
     * Initializes supporting documents list
     */
    private List<Document> docs;

    /**
     * Default full name
     */
    private static final String DEFAULT_FULL_NAME = "no name";
    /**
     * Default NIF
     */
    private static final String DEFAULT_NIF = "no NIF";
    /**
     * Default email
     */
    private static final String DEFAULT_EMAIL = "no email";
    /**
     * Default full name
     */
    private static final String DEFAULT_PHONE_NUMBER = "no phone";

    /**
     * Initializes the default academic qualification's constructor
     */
    public Application() {
        fullName = DEFAULT_FULL_NAME;
        NIF = DEFAULT_NIF;
        email = DEFAULT_EMAIL;
        phoneNumber = DEFAULT_PHONE_NUMBER;
        postalAddress = new PostalAddress();
        this.categories = new ArrayList<>();
        this.academicQualifications = new ArrayList<>();
        this.professionalQualifications = new ArrayList<>();
        this.docs = new ArrayList<>();
    }

    /**
     * Initializes the application's constructor with PostalAddress
     *
     * @param fullName
     * @param NIF
     * @param email
     * @param phoneNumber
     * @param postalAddress
     */
    public Application(String fullName, String NIF, String email,
            String phoneNumber, PostalAddress postalAddress) {
        if ((fullName == null) || (NIF == null) || (phoneNumber == null)
                || (email == null) || (postalAddress == null)
                || (fullName.trim().isEmpty()) || (NIF.trim().isEmpty()) || 
                (phoneNumber.trim().isEmpty()) || (email.trim().isEmpty())) {
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        }
        if (Integer.parseInt(NIF) < 0 || Integer.parseInt(phoneNumber) < 0 || 
                Integer.parseInt(NIF) > 999999999 || Integer.parseInt(phoneNumber) > 999999999) {
            throw new OutOfBoundArgumentException("Out of bound argument!");
        }
        this.fullName = fullName;
        this.NIF = NIF;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.postalAddress = postalAddress;
        this.categories = new ArrayList<>();
        this.academicQualifications = new ArrayList<>();
        this.professionalQualifications = new ArrayList<>();
        this.docs = new ArrayList<>();
    }

    /**
     * Returns the unregistered user's full name
     *
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the unregistered user's email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the unregistered user's phone number
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the unregistered user's NIF
     *
     * @return NIF
     */
    public String getNIF() {
        return this.NIF;
    }

    /**
     * Returns the unregistered user's
     *
     * @return postal address
     */
    public PostalAddress getPostalAddress() {
        return this.postalAddress;
    }

    /**
     * Returns a list with all service categories of the application
     *
     * @return list of categories
     */
    public List<Category> getCategoryList() {
        return new ArrayList<>(categories);
    }

    /**
     * Returns a list with all academic qualifications of the application
     *
     * @return list of academic qualifications
     */
    public List<AcademicQualification> getAcademicQualificationList() {
        return new ArrayList<>(academicQualifications);
    }

    /**
     * Returns a list with all professional qualifications of the application
     *
     * @return list of professional qualifications
     */
    public List<ProfessionalQualification> getProfessionalQualificationList() {
        return new ArrayList<>(professionalQualifications);
    }

    /**
     * Returns a list with all documents of the application
     *
     * @return list of documents
     */
    public List<Document> getDocumentList() {
        return new ArrayList<>(docs);
    }

    /**
     * Adds a new category to the application
     *
     * @param newCategory
     * @return true if added or false if already exists
     */
    public boolean addCategory(Category newCategory) {
        if (validateCategory(newCategory)) {
            this.categories.add(newCategory);
            return true;
        }
        return false;
    }

    /**
     * Adds a new academic qualification to the application
     *
     * @param newAcademicQualification
     * @return true if added or false if already exists
     */
    public boolean addAcademicQualification(AcademicQualification newAcademicQualification) {
        if (validateAcadQualif(newAcademicQualification)) {
            this.academicQualifications.add(newAcademicQualification);
            return true;
        }
        return false;
    }

    /**
     * Adds a new professional qualification to the application
     *
     * @param newprofessionalQualification
     * @return true if added or false if already exists
     */
    public boolean addProfessionalQualification(ProfessionalQualification newprofessionalQualification) {
        if (validateProfQualif(newprofessionalQualification)) {
            this.professionalQualifications.add(newprofessionalQualification);
            return true;
        }
        return false;
    }

    /**
     * Adds a new supporting documents to the application
     *
     * @param newDocument
     * @return true if added or false if already exists
     */
    public boolean addDocument(Document newDocument) {
        if (validateDocument(newDocument)) {
            this.docs.add(newDocument);
            return true;
        }
        return false;
    }

    /**
     * Adds a new postal address to the application
     *
     * @param postalAddress
     */
    public void addPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Override
    public String toString() {
        return String.format("APPLICATION DETAILS%n%nFull Name: %s%nNIF: %s%n"
                + "Email: %s%nPhone Number: %s%n%s%n",
                this.fullName, this.NIF, this.email, this.phoneNumber, this.postalAddress);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/

        // self check
        if (this == o) {
            return true;
        }
        // null check
        if (o == null) {
            return false;
        }
        // type check and cast
        if (getClass() != o.getClass()) {
            return false;
        }
        // field comparison
        Application obj = (Application) o;
        return (Objects.equals(email, obj.email) || Objects.equals(NIF, obj.NIF));
    }

    private boolean validateAcadQualif(AcademicQualification aq) {
        return !getAcademicQualificationList().contains(aq);
    }

    private boolean validateProfQualif(ProfessionalQualification pq) {
        return !getProfessionalQualificationList().contains(pq);
    }

    private boolean validateDocument(Document doc) {
        return !getDocumentList().contains(doc);
    }

    private boolean validateCategory(Category cat) {
        return (!getCategoryList().contains(cat) && cat != null);
    }
}
