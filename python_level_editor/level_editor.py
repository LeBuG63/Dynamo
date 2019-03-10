from tkinter import *

import pickle

RATIO = 2
WIN_WIDTH = 2560/RATIO
WIN_HEIGHT = 1440/RATIO 

N_BLOCK_HEIGHT = 10
N_BLOCK_WIDTH = 23

BLOCK_WIDTH = WIN_WIDTH/N_BLOCK_WIDTH

BLOCK_HEIGHT = int(WIN_HEIGHT/N_BLOCK_HEIGHT)

NUM_VOID = 0
NUM_PLATEFORM = 1
NUM_PIECE_NORMAL = 2
NUM_PIECE_HIGH = 3

PAGE = 0
MAX_PAGE = 50

map_width = N_BLOCK_WIDTH
map = []
for i in range(MAX_PAGE):
    map.append([[0] * N_BLOCK_HEIGHT for i in range(map_width)])

OBJECT_SELECTED = NUM_PLATEFORM

def pxToDp(px):
    return int(px / (560.0 / 160.0)) 

def clicked(event):
    yy = int((event.y / BLOCK_HEIGHT)) 
    xx = int((event.x / BLOCK_WIDTH))
    y = yy * BLOCK_HEIGHT
    x = xx * BLOCK_WIDTH

    color = ['black', 'white', 'red', 'yellow']

    canvas.create_rectangle(x, y, x + BLOCK_WIDTH, y + BLOCK_HEIGHT, fill=color[OBJECT_SELECTED])

    map[PAGE][xx][yy] = OBJECT_SELECTED

def save(event = None):
    file = open("level.txt", "w")
    filesave = open("save_level", "wb")

    plateformsList = []
    piecesNormList = []
    piecesHighList = []

    print("sauvegarde en cours")

    for p in range(MAX_PAGE):
        mapmark = [[False] * N_BLOCK_HEIGHT for i in range(N_BLOCK_WIDTH)]
        for y in range(N_BLOCK_HEIGHT):
            for x in range(N_BLOCK_WIDTH):
                n = map[p][x][y]

                finX = pxToDp((p * N_BLOCK_WIDTH * BLOCK_WIDTH) + x * BLOCK_WIDTH)
                finY = pxToDp((y * 0.9) * BLOCK_HEIGHT)

                if n == NUM_PLATEFORM and mapmark[x][y] == False:
                    mapmark[x][y] = True
                    length = 1
                    xx = x
                    stop = False
                    while not stop:
                        if xx + 1 < N_BLOCK_WIDTH:
                            mapmark[xx][y] = True
                            xx += 1
                            if (map[p][xx][y] != NUM_PLATEFORM or mapmark[xx][y] == True):
                                stop = True
                                plateformsList.append([finX, finY, length])
                            else:
                                mapmark[xx][y] = True
                                length += 1
                        else:
                            stop = True
                            plateformsList.append([finX, finY, length])
                elif n == NUM_PIECE_NORMAL: 
                    piecesNormList.append([finX, finY])
                elif n == NUM_PIECE_HIGH: 
                    piecesHighList.append([finX, finY])

    pickle.dump(map, filesave)

    file.write("PLATEFORMS\n");
    for p in plateformsList:
        file.write("SIMPLE " + str(p[0]) + " " + str(p[1]) + " " + str(p[2]) + "\n")

    file.write("PIECES\n");
    for p in piecesNormList:
        file.write("LOW " + str(p[0]) + " " + str(p[1]) + "\n")
    for p in piecesNormList:
        file.write("NORMAL " + str(p[0]) + " " + str(p[1]) + "\n")


    print("sauvegarde fini")

    filesave.close()
    file.close()

def read(e = None):
    global map 
    global PAGE

    page = 0

    filesave = open("save_level", "rb")
    map = pickle.load(filesave)
    filesave.close()

    update_canvas()

def select_plateform(e = None):
    global OBJECT_SELECTED
    OBJECT_SELECTED = NUM_PLATEFORM

def select_piece_norm(e = None):
    global OBJECT_SELECTED
    OBJECT_SELECTED = NUM_PIECE_NORMAL

def select_piece_high(e = None):
    global OBJECT_SELECTED
    OBJECT_SELECTED = NUM_PIECE_HIGH

def select_void(e = None):
    global OBJECT_SELECTED
    OBJECT_SELECTED = NUM_VOID

def update_canvas():
    color = ['black', 'white', 'red', 'yellow']
    for y in range(N_BLOCK_HEIGHT):
        for x in range(map_width):
            n = map[PAGE][x][y]

            xx = x * BLOCK_WIDTH 
            yy = y * BLOCK_HEIGHT

            canvas.create_rectangle(xx, yy, xx + BLOCK_WIDTH, yy + BLOCK_HEIGHT, fill=color[n])

def plus_page(e = None):
    global PAGE
    PAGE += 1
    update_canvas()

def minus_page(e = None):
    global PAGE
    PAGE -= 1
    update_canvas()

window = Tk()
canvas = Canvas(window, width=WIN_WIDTH, height=WIN_HEIGHT, background='black')

canvas.focus_set()
canvas.bind('s', save)
canvas.bind('q', select_plateform)
canvas.bind('w', select_piece_norm)
canvas.bind('e', select_piece_high)
canvas.bind('t', select_void)
canvas.bind('a', minus_page)
canvas.bind('d', plus_page)
canvas.bind('r', read)
canvas.bind("<Button-1>", clicked)

canvas.pack()

window.mainloop()
