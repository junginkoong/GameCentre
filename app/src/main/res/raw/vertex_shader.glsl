attribute vec4 a_Position; // generate an attribute 4 vector (x, y, z, w)

void main(){

    gl_Position = a_Position; // final position of the vertex

}