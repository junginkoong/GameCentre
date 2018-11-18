#version 120

precision mediump float; // choose between lowp, mediump, highp for precision of data type

uniform vec4 u_Color; // generate a uniform 4 vector that (r, g, b, alpha)

void main() {

    gl_FragColor = u_Color; // final colour of the fragment

}
