#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MWIDTH 500
#define MHEIGHT 500

int a[MWIDTH][MHEIGHT], b[MWIDTH][MHEIGHT], mul[MWIDTH][MHEIGHT], r, c, i, j, k;

int main()
{
    clock_t startProg, stopGen, stopProg;
    startProg = clock();
    int u, o;

    for (o = 0; o < MWIDTH; o++)
    {
        for (u = 0; u < MHEIGHT; u++)
        {
            a[o][u] = rand() % 10;
        }
        for (u = 0; u < MHEIGHT; u++)
        {
            b[o][u] = rand() % 10;
        }
    }
    stopGen = clock();
    system("cls");
    r = MHEIGHT;
    c = MWIDTH;

    printf("Multiplying the matrix...\n");
    for (i = 0; i < r; i++)
    {
        for (k = 0; k < c; k++)
        {
            for (j = 0; j < c; j++)
            {
                mul[i][j] += a[i][k] * b[k][j];
            }
        }
    }

    stopProg = clock();
    printf("whole program took %d time\n", stopProg - startProg);
    printf("generating took %d time\n", stopGen - startProg);
    printf("multiplication took %d time\n", stopProg - stopGen);


}