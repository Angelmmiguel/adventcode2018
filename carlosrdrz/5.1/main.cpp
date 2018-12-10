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

int main(int argc, char* argv[]) {
  std::ifstream fs(argv[1], std::ifstream::in);
  std::string line;
  std::getline(fs, line);

  fs.close();

  auto str = new char[line.length()];
  int resultChars = 0;
  std::strcpy(str, line.c_str());

  for (int i = 0; i < line.length() - 1; i++) {
    if (str[i] == '0') continue;

    auto j = (i == line.length() - 2) ? line.length() - 1 : nextChar(str, i);

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
  for (int i = 0; i < line.length() - 1; i++) {
    if (str[i] == '0') continue;
    result[currentChar++] = str[i];
  }

  std::cout << "Result " << std::strlen(result) << std::endl;

  delete[] str;

  return EXIT_SUCCESS;
}
