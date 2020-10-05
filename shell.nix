{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  inputsFrom = [ (pkgs.callPackage ./derivation.nix {}) ];
  nativeBuildInputs = [ pkgs.clang_39 ];
}
