#include <iostream>
#include <fstream>
#include <cctype>
#include <cstring>

int prevChar(char *string, int current) {
  int next_pos_rel = 1;

  while (true) {
    if (current - next_pos_rel == -1) return current;
    if (string[current - next_pos_rel] != '0') return current - next_pos_rel;
    else next_pos_rel++;
  }
}

int nextChar(char *string, int current) {
  int next_pos_rel = 1;

  while (true) {
    if (string[current + next_pos_rel] != '0') return current + next_pos_rel;
    else next_pos_rel++;
  }
}

bool react(char one, char two) {
  return std::tolower(one) == std::tolower(two) && one != two;
}

int polymerReactionSize(char* str) {
  int resultChars = 0;
  auto len = std::strlen(str);

  for (int i = 0; i < len - 1; i++) {
    if (str[i] == '0') continue;

    auto j = (i == len - 2) ? len - 1 : nextChar(str, i);

    if (react(str[i], str[j])) {
      str[i] = '0';
      str[j] = '0';
      i = prevChar(str, i) - 1;
    } else {
      resultChars++;
    }
  }

  auto result = new char[resultChars+1];
  int currentChar = 0;
  for (int i = 0; i < len - 1; i++) {
    if (str[i] == '0') continue;
    result[currentChar++] = str[i];
  }

  return std::strlen(result);
}

void removeUnit(char* str, char which) {
  auto len = std::strlen(str);

  for (int i = 0; i < len - 1; i++) {
    if (str[i] == '0') continue;

    if (std::tolower(str[i]) == which) {
      str[i] = '0';
    }
  }
}

int main(int argc, char* argv[]) {
  char units[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                   'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                   'u', 'v', 'w', 'x', 'y', 'z' };

  std::ifstream fs(argv[1], std::ifstream::in);
  std::string line;
  std::getline(fs, line);

  fs.close();

  for (auto unit : units) {
    auto str = new char[line.length()];
    std::strcpy(str, line.c_str());
    removeUnit(str, unit);
    std::cout << unit << " " << polymerReactionSize(str) << std::endl;
    delete[] str;
  }

  return EXIT_SUCCESS;
}
