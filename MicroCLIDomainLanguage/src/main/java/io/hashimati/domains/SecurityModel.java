package io.hashimati.domains;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.hashimati.microcli.utils.Visitor;

import java.util.ArrayList;

//@Data
public class SecurityModel
{
    private String securityType;
    private ArrayList<String> roles;
    private boolean propagate;
    private boolean https;
    private String securityPackage;

    public String getSecurityPackage() {
        return securityPackage;
    }

    public void setSecurityPackage(String securityPackage) {
        this.securityPackage = securityPackage;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public boolean isPropagate() {
        return propagate;
    }

    public void setPropagate(boolean propagate) {
        this.propagate = propagate;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public SecurityModel visit(Visitor<SecurityModel> visitor)
    {
        return visitor.visit(this);
    }
}
