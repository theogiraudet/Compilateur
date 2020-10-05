{ stdenv, jdk, gradle }:

stdenv.mkDerivation {
  name = "pds-tp2-java-etu";

  src = ./.;

  nativeBuildInputs = [ jdk gradle ]; # note : antlr4 is downloaded by gradle

  buildPhase = ''
    export GRADLE_USER_HOME=$(mktemp -d)
    gradle --no-daemon build
  '';

  # dumb
  installPhase = ''
    mkdir $out
    mv build/libs/*.jar $out
  '';
}
