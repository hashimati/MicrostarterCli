package ${securityPackage}.mongoReactive

{securityPackage}


interface PasswordEncoder {
     String encode(String rawPassword)
     boolean matches(String rawPassword, String encodedPassword)
     boolean upgradeEncoding(String encodedPassword)
}
