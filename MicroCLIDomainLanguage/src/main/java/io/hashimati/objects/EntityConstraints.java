package io.hashimati.objects;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import io.hashimati.parsers.utils.Visitor;

public class EntityConstraints
{

    private boolean enabled = false;
    //will be used for SQL.
    private boolean nullable,
    primary, foreignKey, notEmpty;


    private Long min =null, max = null;
    private Double decimalMin = null, decimalMax = null;

    //are used for String datatype only;
    private String pattern = null;
    private Long maxSize = null, minSize = null;
    private boolean digits;
    private boolean notBlank;
    private boolean required;
    private boolean unique;
    private boolean email;
    private boolean future;
    private boolean url;
    private boolean creditCard;
//    private String dateVaildation;
    private boolean notempty;


    public EntityConstraints() {
        nullable =true;
        foreignKey = false;
        primary = false;
        notEmpty = false;
        ;
    }

    public EntityConstraints(boolean nullable, boolean primary, boolean foreignKey, boolean notEmpty, long min,
                             long max, String pattern, long maxSize, long minSize, boolean digits, boolean notBlank,
                             boolean email) {
        this.nullable = nullable;
        this.primary = primary;
        this.foreignKey = foreignKey;
        this.notEmpty = notEmpty;
        this.min = min;
        this.max = max;
        this.pattern = pattern;
        this.maxSize = maxSize;
        this.minSize = minSize;
        this.digits = digits;
        this.notBlank = notBlank;
        this.email = email;
    }

    private boolean isValidMinMax(){
        return max > min;
    }
  //  private boolean isValidSize()
//    {
//        return size >0;
//    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public boolean isNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public Long getMinSize() {
        return minSize;
    }

    public void setMinSize(Long minSize) {
        this.minSize = minSize;
    }

    public boolean isDigits() {
        return digits;
    }

    public void setDigits(boolean digits) {
        this.digits = digits;
    }

    public boolean isNotBlank() {
        return notBlank;
    }

    public void setNotBlank(boolean notBlank) {
        this.notBlank = notBlank;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    
    public String getMaxExpression(){

        return max != null?"\t@Max("+max.longValue()+")\n":"";
    }
    
    public String getMinExpression(){
        return min != null?"\t@Min("+min.longValue()+")\n":"";
    }
    
    public String getDecimalMaxExpression(){
        return max != null?"\t@DecimalMax("+decimalMax.doubleValue()+")\n":"";
    }
    public String getDecimalSizeExpressionGorm()
    {
        if((decimalMin != null && decimalMin <0) && ((decimalMax != null && decimalMax < 0)))
            return "";
        return ((decimalMin != null && decimalMin >= 0)? decimalMin.longValue():0)+ ".."+ ((decimalMax != null && max >= 0)?decimalMax.longValue() : (decimalMin!=null?decimalMin.longValue():0)+1);
    }
    
    public String getDecimalMinExpression(){
        return min != null?"\t@DecimalMin("+decimalMin.doubleValue()+")\n":"";
    }
    
    public String getFutureExpression(){
        return isFuture()?"\t@Future\n":"";
    }

    
    public String getUrlGorm()
    {
        return url? "url: true":"";
    }

    
    public String creditCardGorm()
    {
        return creditCard? "creditCard:true":"";

    }

    
    public String getSizeExpression()
    {



        if((min != null && min <0) && ((max != null && max < 0)))
            return "";

        return "\t@Size("+((max != null && max >= 0)?" max="+max.longValue() + "," : "")+((min != null && min >= 0)?" min ="+ min.longValue():"")+")\n";
    }


    
    public String getSizeExpressionGorm()
    {



        if((min != null && min <0) && ((max != null && max < 0)))
            return "";

        return "size: "+ ((min != null && min >= 0)? min.longValue():0)+ ".."+ ((max != null && max >= 0)?max.longValue() : (min!= null?min.longValue():1000)+1);
    }
    
    public String getCollectionSizeExpression()
    {
        if((minSize != null && minSize <0) && (maxSize != null && maxSize < 0))
            return "";
        return "\t@Size("+((maxSize !=null && maxSize >= 0)?" max="+maxSize.longValue() + "," : "")+((minSize != null && minSize >= 0)?" min ="+ minSize.longValue():"")+")\n";
    }
    
    public String getNotNullExpression()
    {

        return required ?"\t@NotNull\n": "";
    }
    
    public String getNotNullExpressionGorm()
    {

        return required ?"nullable:false": "nullable:true";
    }
    
    public String getEmailExpression()
    {
        return email? "\t@Email\n":"";
    }

    
    public String getEmailExpressionGorm()
    {
        return email? "email:true":"";
    }

    
    public String getPatternExpression()
    {
        return pattern== null|| pattern.isEmpty()  ?"":"\t@Pattern(regexp = \""+pattern+"\")\n";
    }
    
    public String getPatternExpressionGorm()
    {
        return pattern== null|| pattern.isEmpty()  ?"": new StringBuilder().append("matches:\"").append(pattern).append("\"").toString();
    }
    
    public String getNotBlankExpression()
    {
        return notEmpty? "\t@NotEmpty\n":"";
    }

    
    public String getNotBlankExpressionGorm()
    {
        return notEmpty? "blank:true": "blank:false";
    }
    
    public String getUniqueExperession()
    {

        return unique? "\t@Column(unique = true)\n": "";

    }
    
    public String getUniqueExperessionGorm(){
        return unique? "unique:true": "unique:false";
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFuture() {
        return future;
    }

    public void setFuture(boolean future) {
        this.future = future;
    }

    public Double getDecimalMin() {
        return decimalMin;
    }

    public void setDecimalMin(Double decimalMin) {
        this.decimalMin = decimalMin;
    }

    public Double getDecimalMax() {
        return decimalMax;
    }

    public void setDecimalMax(Double decimalMax) {
        this.decimalMax = decimalMax;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

//    public String getDateVaildation() {
//        return dateVaildation;
//    }
//
//    public void setDateVaildation(String dateVaildation) {
//        this.dateVaildation = dateVaildation;
//    }

    public boolean isNotempty() {
        return notempty;
    }

    public void setNotempty(boolean notempty) {
        this.notempty = notempty;
    }

//    public String getDateValidationExepression() {
//        if(!dateVaildation.equalsIgnoreCase("none"))
//            return "@"+ dateVaildation + "\n";
//        else
//            return "";
//    }
    public EntityConstraints visit(Visitor<EntityConstraints> visitor)
    {
        return visitor.visit(this);
    }

    public boolean isUrl() {
        return url;
    }

    public void setUrl(boolean url) {
        this.url = url;
    }

    public boolean isCreditCard() {
        return creditCard;
    }

    public void setCreditCard(boolean creditCard) {
        this.creditCard = creditCard;
    }


    
    public String getMinExpressionGorm() {

        if(min != null)
            return "min= "+ min.longValue();
        return "";
    }
    
    public String getMaxExpressionGorm() {

        if(max != null)
            return "max= "+ max.longValue();
        return "";
    }
    
    public String getMinDecimalExpressionGorm() {

        if(decimalMin != null)
            return "min= "+ decimalMin.longValue();
        return "";
    }
    
    public String getMaxDecimalExpressionGorm() {

        if(decimalMax != null)
            return "max= "+ decimalMax.longValue();
        return "";
    }
}

